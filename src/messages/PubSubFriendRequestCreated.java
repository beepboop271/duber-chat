package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;

public class PubSubFriendRequestCreated extends PubSubDirectMessage {
  private static final long serialVersionUID = 0L;

  private final long friendRequestId;
  private final String sourceUsername;
  private final String targetUsername;

  public PubSubFriendRequestCreated(
    long friendRequestId,
    String sourceUsername,
    String targetUsername
  ) {
    this.friendRequestId = friendRequestId;
    this.sourceUsername = sourceUsername;
    this.targetUsername = targetUsername;
  }

  @Override
  public String toString() {
    return "PublishFriendRequestReceived[friendRequestId="
      +this.friendRequestId
      +", sourceUsername="
      +this.sourceUsername
      +", targetUsername="
      +this.targetUsername
      +"]";
  }

  @Override
  public void execute(ReDuber db, long recipient) throws InterruptedException {
    try {
      if (recipient == -1) {
        db.publishSingle(
          "friendRequests."+this.friendRequestId+".sourceUserId",
          this
        ).get();
        db.publishSingle(
          "friendRequests."+this.friendRequestId+".targetUserId",
          this
        ).get();
      } else {
        db.publishDirect(recipient, this).get();
      }
    } catch (ExecutionException e) {
      Log.warn("Failed to submit Publish job", "MessagePublisher", this, e);
    }
  }

  public long getFriendRequestId() {
    return this.friendRequestId;
  }

  public String getSourceUsername() {
    return this.sourceUsername;
  }

  public String getTargetUsername() {
    return this.targetUsername;
  }
}
