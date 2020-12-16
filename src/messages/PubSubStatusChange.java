package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;

/**
 * A server-published message which indicates that a
 * friend's status or status message has changed.
 *
 * @author Kevin Qiao
 */
public class PubSubStatusChange extends PubSubMessage {
  private static final long serialVersionUID = 0L;

  private final long userId;
  private final String userStatus;
  private final String userMessage;

  public PubSubStatusChange(long userId, String userStatus, String message) {
    this.userId = userId;
    this.userStatus = userStatus;
    this.userMessage = message;
  }

  @Override
  public String toString() {
    return "PubSubStatusChange[userId="
      +this.userId
      +", userStatus="
      +this.userStatus
      +", userMessage="
      +this.userMessage
      +"]";
  }

  @Override
  public void execute(ReDuber db) throws InterruptedException {
    try {
      db.publishMany("users."+this.userId+".friends", this).get();
    } catch (ExecutionException e) {
      Log.warn("Failed to submit Publish job", "MessagePublisher", this, e);
    }
  }

  public long getUserId() {
    return this.userId;
  }

  public String getUserStatus() {
    return this.userStatus;
  }

  public String getUserMessage() {
    return this.userMessage;
  }
}
