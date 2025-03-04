package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

/**
 * A mesage which represents a change in user status.
 *
 * @author Kevin Qiao
 */
public class DoStatusChange extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private String userStatus;
  private String userMessage;

  public DoStatusChange(String status, String message) {
    this.userMessage = message;
    this.userStatus = status;
  }

  @Override
  public String toString() {
    return "DoStatusChange[userStatus="
      +this.userStatus
      +", userMessage="
      +this.userMessage
      +"]";
  }

  @Override
  public Reply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return Reply.noPermission("Not logged in");
    }

    try {
      CompletableFuture
        .allOf(
          db.set("users."+user.getUserId()+".status", this.userStatus),
          db.set("users."+user.getUserId()+".message", this.userMessage)
        )
        .get();
      new PubSubStatusChange(
        user.getUserId(),
        this.userStatus,
        this.userMessage
      ).execute(db);
      return Reply.ok();
    } catch (ExecutionException e) {
      Log.warn("Failed to change status", "MessageHandler", this, e);
    }
    return Reply.serverUnknown();
  }
}
