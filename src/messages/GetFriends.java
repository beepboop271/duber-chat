package messages;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

/**
 * A message for the retrieval of a user's friends.
 *
 * @author Kevin Qiao
 */
public class GetFriends extends GetMessage {
  private static final long serialVersionUID = 0L;

  public GetFriends() {
  }

  @Override
  public String toString() {
    return "GetFriends[]";
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return new GetFriendsReply(Reply.Status.E_NO_PERMISSION, "Not logged in");
    }

    try {
      return new GetFriendsReply(
        db.setGet("users."+user.getUserId()+".friends").get()
      );
    } catch (ExecutionException e) {
      Log.warn("Failed to get friends", "MessageHandler", this, e);
    }
    return new GetFriendsReply(Reply.Status.E_SERVER_UNKNOWN);
  }

  /**
   * A message containing a requested array of a user's
   * friends.
   *
   * @author Kevin Qiao
   */
  public static class GetFriendsReply extends GetReply {
    private static final long serialVersionUID = 0L;

    private final Long[] userIds;

    public GetFriendsReply(Long[] userIds) {
      super(Reply.Status.OK);
      this.userIds = userIds;
    }

    public GetFriendsReply(Reply.Status status) {
      super(status);
      this.userIds = null;
    }

    public GetFriendsReply(Reply.Status status, String detailMessage) {
      super(status, detailMessage);
      this.userIds = null;
    }

    @Override
    public String toString() {
      return "GetFriendsReply[userIds="+Arrays.toString(this.userIds)+"]";
    }

    public Long[] getUserIds() {
      return this.userIds;
    }
  }
}
