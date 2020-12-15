package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public class GetFriends extends GetMessage {
  private static final long serialVersionUID = 0L;

  public GetFriends() {
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    // TODO Auto-generated method stub
    return null;
  }

  public static class GetFriendsReply extends GetReply {
    private static final long serialVersionUID = 0L;

    private final Long[] userIds;

    public GetFriendsReply(Long[] userIds) {
      super(Reply.Status.OK);
      this.userIds = userIds;
    }

    public GetFriendsReply(Status status, String detailMessage) {
      super(status, detailMessage);
      this.userIds = null;
    }
    
    public Long[] getUserIds() {
      return this.userIds;
    }
  }
}
