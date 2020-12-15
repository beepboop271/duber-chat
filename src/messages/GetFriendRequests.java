package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

public class GetFriendRequests extends GetMessage {
  private static final long serialVersionUID = 0L;

  public GetFriendRequests() {
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return new GetFriendRequestsReply(
        Reply.Status.E_NO_PERMISSION,
        "Not logged in"
      );
    }

    try {
      Long[] outgoing = db.listGet("users."+user.getUserId()+".outgoingFriendRequests").get();
      Long[] incoming = db.listGet("users."+user.getUserId()+".incomingFriendRequests").get();

      Long[] requests = new Long[outgoing.length+incoming.length];
      String[] sources = new String[requests.length];
      String[] targets = new String[requests.length];
      int i = 0;
      for (long id : outgoing) {
        requests[i] = id;
        sources[i] =
          db.stringGet(
            "users."
              +db.longGet("friendRequests."+id+".sourceUserId")
              +".username"
          ).get();
        targets[i] =
          db.stringGet(
            "users."
              +db.longGet("friendRequests."+id+".targetUserId")
              +".username"
          ).get();
        ++i;
      }
      for (long id : incoming) {
        requests[i] = id;
        sources[i] =
          db.stringGet(
            "users."
              +db.longGet("friendRequests."+id+".sourceUserId")
              +".username"
          ).get();
        targets[i] =
          db.stringGet(
            "users."
              +db.longGet("friendRequests."+id+".targetUserId")
              +".username"
          ).get();
        ++i;
      }
      return new GetFriendRequestsReply(requests, sources, targets);
    } catch (ExecutionException e) {
      Log.warn("Failed to get friend requests", "MessageHandler", this, e);
    }
    return new GetFriendRequestsReply(Reply.Status.E_SERVER_UNKNOWN);
  }

  public static class GetFriendRequestsReply extends GetReply {
    private static final long serialVersionUID = 0L;

    private final Long[] friendRequestIds;
    private final String[] sourceUsernames;
    private final String[] targetUsernames;

    public GetFriendRequestsReply(
      Long[] friendRequestIds,
      String[] sourceUsernames,
      String[] targetUsernames
    ) {
      super(Reply.Status.OK);
      this.friendRequestIds = friendRequestIds;
      this.sourceUsernames = sourceUsernames;
      this.targetUsernames = targetUsernames;
    }

    public GetFriendRequestsReply(Reply.Status status) {
      super(status);
      this.friendRequestIds = null;
      this.sourceUsernames = null;
      this.targetUsernames = null;
    }

    public GetFriendRequestsReply(Reply.Status status, String detailMessage) {
      super(status, detailMessage);
      this.friendRequestIds = null;
      this.sourceUsernames = null;
      this.targetUsernames = null;
    }

    public Long[] getFriendRequestIds() {
      return this.friendRequestIds;
    }

    public String[] getSourceUsernames() {
      return this.sourceUsernames;
    }

    public String[] getTargetUsernames() {
      return this.targetUsernames;
    }
  }
}
