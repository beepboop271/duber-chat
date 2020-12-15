package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

public class GetUser extends GetMessage {
  private static final long serialVersionUID = 0L;

  private final long userId;

  public GetUser(long userId) {
    this.userId = userId;
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return new GetUserReply(Reply.Status.E_NO_PERMISSION, "Not logged in");
    }

    try {
      String username = db.stringGet("users."+this.userId+".username").get();
      if (username == null) {
        return new GetUserReply(
          Reply.Status.E_NOT_EXISTS,
          "User does not exist"
        );
      }
      if (
        db.setContains("users."+user.getUserId()+".friends", this.userId).get()
          != ReDuber.Status.TRUE
      ) {
        return new GetUserReply(
          Reply.Status.E_NO_PERMISSION,
          "Cannot get user you are not friends with"
        );
      }
      String userStatus = db.stringGet("users."+this.userId+".status").get();
      String userMessage = db.stringGet("users."+this.userId+".message").get();
      return new GetUserReply(username, userStatus, userMessage);
    } catch (ExecutionException e) {
      Log.warn("Failed to get user", "MessageHandler", this, e);
    }
    return new GetUserReply(Reply.Status.E_SERVER_UNKNOWN);
  }

  public static class GetUserReply extends GetReply {
    private static final long serialVersionUID = 0L;

    private final String username;
    private final String userStatus;
    private final String userMessage;

    public GetUserReply(
      String username,
      String userStatus,
      String userMessage
    ) {
      super(Reply.Status.OK);
      this.username = username;
      this.userStatus = userStatus;
      this.userMessage = userMessage;
    }

    public GetUserReply(Reply.Status status) {
      super(status);
      this.username = null;
      this.userStatus = null;
      this.userMessage = null;
    }

    public GetUserReply(Reply.Status status, String detailMessage) {
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
