package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

public class GetChats extends GetMessage {
  private static final long serialVersionUID = 0L;

  public GetChats() {
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

    public Long[] getChatIds() {
      return this.chatIds;
    }
  }
}
