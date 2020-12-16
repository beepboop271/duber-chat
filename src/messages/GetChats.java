package messages;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

/**
 * A message for retrieval of the chats a user is in.
 *
 * @author Kevin Qiao
 */
public class GetChats extends GetMessage {
  private static final long serialVersionUID = 0L;

  public GetChats() {
  }

  @Override
  public String toString() {
    return "GetChats[]";
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return new GetChatsReply(Reply.Status.E_NO_PERMISSION, "Not logged in");
    }

    try {
      return new GetChatsReply(
        db.setGet("users."+user.getUserId()+".chats").get()
      );
    } catch (ExecutionException e) {
      Log.warn("Failed to get chats", "MessageHandler", this, e);
    }
    return new GetChatsReply(Reply.Status.E_SERVER_UNKNOWN);
  }

  /**
   * A message containing a requested array of chats a user is
   * in.
   *
   * @author Kevin Qiao
   */
  public static class GetChatsReply extends GetReply {
    private static final long serialVersionUID = 0L;

    private final Long[] chatIds;

    public GetChatsReply(Long[] chatIds) {
      super(Reply.Status.OK);
      this.chatIds = chatIds;
    }

    public GetChatsReply(Reply.Status status) {
      super(status);
      this.chatIds = null;
    }

    public GetChatsReply(Reply.Status status, String detailMessage) {
      super(status, detailMessage);
      this.chatIds = null;
    }

    @Override
    public String toString() {
      return "GetChatsReply[chatIds="+Arrays.toString(this.chatIds)+"]";
    }

    public Long[] getChatIds() {
      return this.chatIds;
    }
  }
}
