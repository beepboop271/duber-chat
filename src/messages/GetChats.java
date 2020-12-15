package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public class GetChats extends GetMessage {
  private static final long serialVersionUID = 0L;

  public GetChats() {
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    // TODO Auto-generated method stub
    return null;
  }

  public static class GetChatsReply extends GetReply {
    private static final long serialVersionUID = 0L;

    private final Long[] chatIds;

    public GetChatsReply(Long[] chatIds) {
      super(Reply.Status.OK);
      this.chatIds = chatIds;
    }

    public GetChatsReply(Status status, String detailMessage) {
      super(status, detailMessage);
      this.chatIds = null;
    }

    public Long[] getChatIds() {
      return this.chatIds;
    }
  }
}
