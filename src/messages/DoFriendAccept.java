package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

public class DoFriendAccept extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long friendRequestId;

  public DoFriendAccept(long friendRequestId) {
    this.friendRequestId = friendRequestId;
  }

  @Override
  public String toString() {
    return "DoFriendAccept[friendRequestId="+this.friendRequestId+"]";
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
          "Cannot accept a friend request not addressed to you"
        );
      }

      new PubSubFriendUpdate(targetUserId, PubSubFriendUpdate.Type.ADD)
        .execute(db, sourceUserId);
      new PubSubFriendUpdate(sourceUserId, PubSubFriendUpdate.Type.ADD)
        .execute(db, targetUserId);

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
          db.setAdd("users."+sourceUserId+".friends", targetUserId),
          db.setAdd("users."+targetUserId+".friends", sourceUserId),
          db.longRemove("friendRequests."+this.friendRequestId+".sourceUserId"),
          db.longRemove("friendRequests."+this.friendRequestId+".targetUserId")
        )
        .get();
      return Reply.ok();
    } catch (ExecutionException e) {
      Log.warn("Failed to accept friend request", "MessageHandler", this, e);
    }
    return Reply.serverUnknown();
  }
}
