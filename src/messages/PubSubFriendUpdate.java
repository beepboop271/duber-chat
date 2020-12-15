package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;

public class PubSubFriendUpdate extends PubSubDirectMessage {
  private static final long serialVersionUID = 0L;

  public enum Type {
    ADD,
    REMOVE,
  }

  private final long userId;
  private final Type type;

  public PubSubFriendUpdate(long userId, Type type) {
    this.userId = userId;
    this.type = type;
  }

  @Override
  public String toString() {
    return "PubSubFriendUpdate [type="+this.type+", userId="+this.userId+"]";
  }

  @Override
  public void execute(ReDuber db, long recipient) throws InterruptedException {
    try {
      db.publishDirect(recipient, this).get();
    } catch (ExecutionException e) {
      Log.warn("Failed to submit Publish job", "MessagePublisher", this, e);
    }
  }

  public long getUserId() {
    return this.userId;
  }

  public Type getType() {
    return this.type;
  }
}
