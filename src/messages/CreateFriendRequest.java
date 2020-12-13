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
  public CommandReply execute(
    ReDuber db,
    ConnectedUser user
  ) throws InterruptedException {
    try {
      Long targetUserId = db.longGet("users."+this.targetUsername+".id").get();
      if (targetUserId == null) {
        return CommandReply.notExists("Given username does not exist");
      }
      ReDuber.Status result = db.setContains(
        "users."+user.getUserId()+".friends",
        targetUserId
      ).get();
      if (result == ReDuber.Status.TRUE) {
        return CommandReply.exists("Already friends with the given username");
      } else if (result == ReDuber.Status.FALSE) {
        long requestId = ReDuberId.getId();
        CompletableFuture.allOf(
          db.setAdd("users."+user.getUserId()+".outgoingFriendRequests", requestId),
          db.setAdd("users."+targetUserId+".incomingFriendRequests", requestId),
          db.set("friendRequests."+requestId+".sourceUserId", user.getUserId()),
          db.set("friendRequests."+requestId+".targetUserId", targetUserId)
        ).join();
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
        return CommandReply.ok();
      }
    } catch (ExecutionException e) {
      Log.warn("Failed to create friend request", "MessageHandler", this, e);
    }
    return CommandReply.serverUnknown();
  }
}
