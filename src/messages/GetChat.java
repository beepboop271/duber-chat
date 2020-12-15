package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public class GetChat extends GetMessage {
  private static final long serialVersionUID = 0L;

  private long chatId;

  public GetChat(long chatId) {
    this.chatId = chatId;
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    // TODO Auto-generated method stub
    return null;
  }

  public static class GetChatReply extends GetReply {
    private static final long serialVersionUID = 0L;

    private final String type;
    private final Long[] userIds;
    private final String name;
    private final Long lastMessageId;

    public GetChatReply(
      String type,
      Long[] userIds,
      String name,
      long lastMessageId
    ) {
      super(Reply.Status.OK);
      this.type = type;
      this.userIds = userIds;
      this.name = name;
      this.lastMessageId = lastMessageId;
    }

    public GetChatReply(Status status, String detailMessage) {
      super(status, detailMessage);
      this.type = null;
      this.userIds = null;
      this.name = null;
      this.lastMessageId = null;
    }

    public String getType() {
      return this.type;
    }

    public Long[] getUserIds() {
      return this.userIds;
    }

    public String getName() {
      return this.name;
    }

    public Long getLastMessageId() {
      return this.lastMessageId;
    }
  }
}
