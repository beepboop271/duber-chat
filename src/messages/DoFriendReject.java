package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

/**
 * A message which rejects an incoming friend request.
 *
 * @author Kevin Qiao
 */
public class DoFriendReject extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long friendRequestId;

  public DoFriendReject(long friendRequestId) {
    this.friendRequestId = friendRequestId;
  }

  @Override
  public String toString() {
    return "DoFriendReject[friendRequestId="+this.friendRequestId+"]";
  }

  @Override
  public Reply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return Reply.noPermission("Not logged in");
    }

    try {
      Long sourceUserId = db.longGet(
        "friendRequests."+this.friendRequestId+".sourceUserId"
      ).get();
      if (sourceUserId == null) {
        return Reply.notExists("Friend request does not exist");
      }
      Long targetUserId = db.longGet(
        "friendRequests."+this.friendRequestId+".targetUserId"
      ).get();
      if (user.getUserId() != targetUserId) {
        return Reply.badOperation(
          "Cannot reject a friend request not addressed to you"
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
      Log.warn("Failed to reject friend request", "MessageHandler", this, e);
    }
    return Reply.serverUnknown();
  }
}
