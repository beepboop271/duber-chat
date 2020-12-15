package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import reduber.ReDuberId;
import server.ConnectedUser;

public class CreateMessage extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long chatId;
  private String message;

  public CreateMessage(long chatId, String message) {
    this.chatId = chatId;
    this.message = message;
  }

  @Override
  public String toString() {
    return "CreateMessage[chatId="+this.chatId+", message="+this.message+"]";
  }

  @Override
  public CommandReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return CommandReply.noPermission("Not logged in");
    }

    try {
      ReDuber.Status status = db.setContains(
        "chats."+this.chatId+".members",
        user.getUserId()
      ).get();
      if (status == ReDuber.Status.FALSE) {
        return CommandReply.noPermission(
          "Chat does not exist or you are not a member of the chat"
        );
      } else if (status == ReDuber.Status.TRUE) {
        if (db.stringGet("chats."+this.chatId+".type").get().equals("DM")) {
          Long[] users = db.setGet("chats."+this.chatId+".members").get();
          if (users.length != 2) {
            Log.error(
              "Inconsistent state DM members length != 2",
              "MessageHandler",
              this
            );
            return CommandReply.serverUnknown();
          }
          if (
            db.setContains("users."+users[0]+".friends", users[1]).get()
              == ReDuber.Status.FALSE
          ) {
            return CommandReply.noPermission(
              "You are no longer friends with the other DM user"
            );
          }
        }
        long messageId = ReDuberId.getId();
        long time = System.currentTimeMillis();
        CompletableFuture
          .allOf(
            db.set("messages."+messageId+".author", user.getUserId()),
            db.set("messages."+messageId+".chat", this.chatId),
            db.set("messages."+messageId+".time", time),
            db.set("messages."+messageId+".message", this.message)
          )
          .get();
        new PubSubMessageReceived(
          messageId,
          this.chatId,
          user.getUserId(),
          time,
          this.message
        ).execute(db);
        return CommandReply.ok();
      }
    } catch (ExecutionException e) {
      Log.warn("Failed to create message", "MessageHandler", this, e);
    }
    return CommandReply.serverUnknown();
  }
}
