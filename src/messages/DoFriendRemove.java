package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

public class DoFriendRemove extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long userId;

  public DoFriendRemove(long userId) {
    this.userId = userId;
  }

  @Override
  public CommandReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {

    try {
      ReDuber.Status status = db.setContains(
        "users."+user.getUserId()+".friends",
        this.userId
      ).get();
      if (status == ReDuber.Status.FALSE) {
        return CommandReply.notExists("User does not exist or is not friends with you");
      } else if (status == ReDuber.Status.TRUE) {
        new PubSubFriendUpdate(this.userId, PubSubFriendUpdate.Type.REMOVE)
          .execute(db, user.getUserId());
        new PubSubFriendUpdate(user.getUserId(), PubSubFriendUpdate.Type.REMOVE)
          .execute(db, this.userId);
        
        CompletableFuture.allOf(
          db.setRemove("users."+this.userId+".friends", user.getUserId()),
          db.setRemove("users."+user.getUserId()+".friends", this.userId)
        );
      }
    } catch (ExecutionException e) {
      Log.warn("Failed to remove friend", "MessageHandler", this, e);
    }
    return CommandReply.serverUnknown();
  }
}
