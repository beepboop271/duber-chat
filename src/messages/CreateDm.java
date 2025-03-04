package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import reduber.ReDuberId;
import server.ConnectedUser;

/**
 * A message which creates a DM chat.
 *
 * @author Kevin Qiao
 */
public class CreateDm extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long userId;

  public CreateDm(long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "CreateDm[userId="+this.userId+"]";
  }

  @Override
  public Reply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return Reply.noPermission("Not logged in");
    }

    try {
      ReDuber.Status status = db.setContains(
        "users."+user.getUserId()+".friends",
        this.userId
      ).get();
      if (status == ReDuber.Status.FALSE) {
        return Reply.notExists("User does not exist or is not friends with you");
      } else if (status == ReDuber.Status.TRUE) {
        if (db.longGet("users."+user.getUserId()+".dms."+this.userId).get() != null) {
          return Reply.exists("DM chat exists already");
        }
        long chatId = ReDuberId.getId();
        long messageId = ReDuberId.getId();
        CompletableFuture
          .allOf(
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
          )
          .get();
        new PubSubDmJoined(
          chatId,
          this.userId,
          messageId
        ).execute(db, user.getUserId());
        new PubSubDmJoined(
          chatId,
          user.getUserId(),
          messageId
        ).execute(db, this.userId);
        return Reply.ok();
      }
    } catch (ExecutionException e) {
      Log.warn("Failed to create DM chat", "MessageHandler", this, e);
    }
    return Reply.serverUnknown();
  }
}
