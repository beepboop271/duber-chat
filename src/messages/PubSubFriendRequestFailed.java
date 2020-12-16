package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;

/**
 * A server-published message which indicates that a friend
 * request has been ended without a created friendship.
 * Results from the recipient rejecting or the sender
 * cancelling the request.
 *
 * @author Kevin Qiao
 */
public class PubSubFriendRequestFailed extends PubSubDirectMessage {
  private static final long serialVersionUID = 0L;

  private final long friendRequestId;
  private final String sourceUsername;
  private final String targetUsername;

  public PubSubFriendRequestFailed(
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
    return "PubSubFriendRequestReceived[friendRequestId="
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
