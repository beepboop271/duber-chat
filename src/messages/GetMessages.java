package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public class GetMessages extends GetMessage {
  private static final long serialVersionUID = 0L;

  private long chatId;
  private long startMessageId;
  private int amount;

  public GetMessages(long chatId, long startMessageId, int amount) {
    this.chatId = chatId;
    this.startMessageId = startMessageId;
    this.amount = amount;
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    // TODO Auto-generated method stub
    return null;
  }

  public static class GetMessagesReply extends GetReply {
    private static final long serialVersionUID = 0L;

    private final Long[] messageIds;
    private final Long[] userIds;
    private final Long[] times;
    private final String[] messages;

    public GetMessagesReply(
      Long[] messageIds,
      Long[] userIds,
      Long[] times,
      String[] messages
    ) {
      super(Reply.Status.OK);
      this.messageIds = messageIds;
      this.userIds = userIds;
      this.times = times;
      this.messages = messages;
    }

    public GetMessagesReply(Status status, String detailMessage) {
      super(status, detailMessage);
      this.messageIds = null;
      this.userIds = null;
      this.times = null;
      this.messages = null;
    }

    public Long[] getMessageIds() {
      return this.messageIds;
    }

    public Long[] getUserIds() {
      return this.userIds;
    }

    public Long[] getTimes() {
      return this.times;
    }

    public String[] getMessages() {
      return this.messages;
    }
  }
}
