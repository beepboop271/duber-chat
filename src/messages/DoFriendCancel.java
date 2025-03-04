package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

/**
 * A message which cancels an outgoing friend request that
 * was sent.
 *
 * @author Kevin Qiao
 */
public class DoFriendCancel extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long friendRequestId;

  public DoFriendCancel(long friendRequestId) {
    this.friendRequestId = friendRequestId;
  }

  @Override
  public String toString() {
    return "DoFriendCancel[friendRequestId="+this.friendRequestId+"]";
  }

  @Override
  public Reply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return Reply.noPermission("Not logged in");
    }

    try {
      Long targetUserId = db.longGet(
        "friendRequests."+this.friendRequestId+".targetUserId"
      ).get();
      if (targetUserId == null) {
        return Reply.notExists("Friend request does not exist");
      }
      Long sourceUserId = db.longGet(
        "friendRequests."+this.friendRequestId+".sourceUserId"
      ).get();
      if (user.getUserId() != sourceUserId) {
        return Reply.badOperation(
          "Cannot cancel a friend request not created by you"
        );
      }

      String sourceUsername =
        db.stringGet("users."+sourceUserId+".username").get();
      String targetUsername =
        db.stringGet("users."+targetUserId+".username").get();

      new PubSubFriendRequestFailed(
        this.friendRequestId,
        sourceUsername,
        targetUsername
      ).execute(db, sourceUserId);
      new PubSubFriendRequestFailed(
        this.friendRequestId,
        sourceUsername,
        targetUsername
      ).execute(db, targetUserId);

      CompletableFuture
        .allOf(
          db.setRemove(
            "users."+sourceUserId+".outgoingFriendRequests",
            this.friendRequestId
          ),
          db.setRemove(
            "users."+targetUserId+".incomingFriendRequests",
            this.friendRequestId
          ),
          db.setRemove("users."+sourceUserId+".pendingFriends", targetUserId),
          db.setRemove("users."+targetUserId+".pendingFriends", sourceUserId),
          db.longRemove("friendRequests."+this.friendRequestId+".sourceUserId"),
          db.longRemove("friendRequests."+this.friendRequestId+".targetUserId")
        )
        .get();
      return Reply.ok();
    } catch (ExecutionException e) {
      Log.warn("Failed to cancel friend request", "MessageHandler", this, e);
    }
    return Reply.serverUnknown();
  }
}
