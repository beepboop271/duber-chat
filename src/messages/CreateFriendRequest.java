package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import reduber.ReDuberId;
import server.ConnectedUser;

public class CreateFriendRequest extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private String targetUsername;

  public CreateFriendRequest(String targetUsername) {
    this.targetUsername = targetUsername;
  }

  @Override
  public String toString() {
    return "CreateFriendRequest[targetUsername="+this.targetUsername+"]";
  }

  @Override
  public Reply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return Reply.noPermission("Not logged in");
    }

    try {
      Long targetUserId = db.longGet("users."+this.targetUsername+".id").get();
      if (targetUserId == null) {
        return Reply.notExists("Given username does not exist");
      }

      ReDuber.Status result = db.setContains(
        "users."+user.getUserId()+".friends",
        targetUserId
      ).get();
      if (result == ReDuber.Status.TRUE) {
        return Reply.exists("Already friends with the given username");
      } else if (result != ReDuber.Status.FALSE) {
        return Reply.serverUnknown();
      }

      result = db.setAdd(
        "users."+user.getUserId()+".pendingFriends",
        targetUserId
      ).get();
      if (result == ReDuber.Status.NO_CHANGE) {
        return Reply.exists("A similar friend request already exists");
      } else if (result != ReDuber.Status.OK) {
        return Reply.serverUnknown();
      }

      long requestId = ReDuberId.getId();
      CompletableFuture.allOf(
        db.setAdd(
          "users."+user.getUserId()+".outgoingFriendRequests",
          requestId
        ),
        db.setAdd("users."+targetUserId+".incomingFriendRequests", requestId),
        db.setAdd("users."+targetUserId+".pendingFriends", user.getUserId()),
        db.set("friendRequests."+requestId+".sourceUserId", user.getUserId()),
        db.set("friendRequests."+requestId+".targetUserId", targetUserId)
      ).get();
      new PubSubFriendRequestCreated(
        requestId,
        user.getUsername(),
        this.targetUsername
      ).execute(db, user.getUserId());
      new PubSubFriendRequestCreated(
        requestId,
        user.getUsername(),
        this.targetUsername
      ).execute(db, targetUserId);
      return Reply.ok();
    } catch (ExecutionException e) {
      Log.warn("Failed to create friend request", "MessageHandler", this, e);
    }
    return Reply.serverUnknown();
  }
}
