package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import reduber.ReDuberId;
import server.ConnectedUser;

public class CreateDm extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long userId;

  public CreateDm(long userId) {
    this.userId = userId;
  }

  @Override
  public CommandReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return CommandReply.noPermission("Not logged in");
    }

    try {
      ReDuber.Status status = db.setContains(
        "users."+user.getUserId()+".friends",
        this.userId
      ).get();
      if (status == ReDuber.Status.FALSE) {
        return CommandReply.notExists("User does not exist or is not friends with you");
      } else if (status == ReDuber.Status.TRUE) {
        if (db.longGet("users."+user.getUserId()+".dms."+this.userId) != null) {
          return CommandReply.exists("DM chat exists already");
        }
        long chatId = ReDuberId.getId();
        long messageId = ReDuberId.getId();
        CompletableFuture.allOf(
          db.set("chats."+chatId+".type", "DM"),
          db.setAdd("chats."+chatId+".members", user.getUserId()),
          db.setAdd("chats."+chatId+".members", this.userId),
          db.set("users."+user.getUserId()+".dms."+this.userId, chatId),
          db.set("users."+this.userId+".dms."+user.getUserId(), chatId),
          db.setAdd("users."+user.getUserId()+".chats", chatId),
          db.setAdd("users."+this.userId+".chats", chatId),
          db.set("messages."+messageId+".author", 0),
          db.set("messages."+messageId+".chat", chatId),
          db.set("messages."+messageId+".time", System.currentTimeMillis()),
          db.set(
            "messages."+messageId+".message",
            "<@"+user.getUserId()+"> created a DM with <@"+this.userId+">"
          ),
          db.listAdd("chats."+chatId+".messages", messageId)
        ).join();
        new PubSubDmJoined(
          chatId,
          new Long[] { user.getUserId(), this.userId },
          messageId
        ).execute(db);
        return CommandReply.ok();
      }
    } catch (ExecutionException e) {
      Log.warn("Failed to create DM chat", "MessageHandler", this, e);
    }
    return CommandReply.serverUnknown();
  }
}
