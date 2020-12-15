package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

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
  public CommandReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return CommandReply.noPermission("Not logged in");
    }

    try {
      Long targetUserId = db.longGet(
        "friendRequests."+this.friendRequestId+".targetUserId"
      ).get();
      if (targetUserId == null) {
        return CommandReply.notExists("Friend request does not exist");
      }
      Long sourceUserId = db.longGet(
        "friendRequests."+this.friendRequestId+".sourceUserId"
      ).get();
      if (user.getUserId() != sourceUserId) {
        return CommandReply.badOperation(
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
          db.longRemove("friendRequests."+this.friendRequestId+".sourceUserId"),
          db.longRemove("friendRequests."+this.friendRequestId+".targetUserId")
        )
        .get();
      return CommandReply.ok();
    } catch (ExecutionException e) {
      Log.warn("Failed to cancel friend request", "MessageHandler", this, e);
    }
    return CommandReply.serverUnknown();
  }
}
