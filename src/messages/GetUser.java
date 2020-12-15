package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public class GetUser extends GetMessage {
  private static final long serialVersionUID = 0L;

  private long userId;

  public GetUser(long userId) {
    this.userId = userId;
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    // TODO Auto-generated method stub
    return null;
  }

  public static class GetUserReply extends GetReply {
    private static final long serialVersionUID = 0L;

    private final String username;
    private final String userStatus;
    private final String userMessage;

    public GetUserReply(String username, String userStatus, String userMessage) {
      super(Reply.Status.OK);
      this.username = username;
      this.userStatus = userStatus;
      this.userMessage = userMessage;
    }

    public GetUserReply(Status status, String detailMessage) {
      super(status, detailMessage);
      this.username = null;
      this.userStatus = null;
      this.userMessage = null;
    }

    public String getUsername() {
      return this.username;
    }

    public String getUserStatus() {
      return this.userStatus;
    }

    public String getUserMessage() {
      return this.userMessage;
    }
  }
}
