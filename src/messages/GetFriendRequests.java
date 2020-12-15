package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public class GetFriendRequests extends GetMessage {
  private static final long serialVersionUID = 0L;

  public GetFriendRequests() {
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    // TODO Auto-generated method stub
    return null;
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

    public GetFriendRequestsReply(Status status, String detailMessage) {
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
