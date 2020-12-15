package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

public class GetMessages extends GetMessage {
  private static final long serialVersionUID = 0L;

  private final long chatId;
  private final long startMessageId;
  private final int amount;

  public GetMessages(long chatId, long startMessageId, int amount) {
    this.chatId = chatId;
    this.startMessageId = startMessageId;
    this.amount = amount;
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return new GetMessagesReply(
        Reply.Status.E_NO_PERMISSION,
        "Not logged in"
      );
    }

    try {
      if (
        db.setContains("chats."+this.chatId+".members", user.getUserId()).get()
          != ReDuber.Status.TRUE
      ) {
        return new GetMessagesReply(
          Reply.Status.E_NO_PERMISSION,
          "Cannot get messages in a chat you are not in"
        );
      }
      Long[] messageIds = db.listGetRange(
        "chats."+this.chatId+".messages",
        this.startMessageId,
        this.amount
      ).get();
      Long[] authors = new Long[messageIds.length];
      Long[] times = new Long[messageIds.length];
      String[] messages = new String[messageIds.length];
      for (int i = 0; i < messageIds.length; ++i) {
        authors[i] = db.longGet("messages."+messageIds[i]+".author").get();
        times[i] = db.longGet("messages."+messageIds[i]+".time").get();
        messages[i] = db.stringGet("messages."+messageIds[i]+".message").get();
      }
      return new GetMessagesReply(messageIds, authors, times, messages);
    } catch (ExecutionException e) {
      Log.warn("Failed to get messages", "MessageHandler", this, e);
    }
    return new GetMessagesReply(Reply.Status.E_SERVER_UNKNOWN);
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

    public GetMessagesReply(Reply.Status status) {
      super(status);
      this.messageIds = null;
      this.userIds = null;
      this.times = null;
      this.messages = null;
    }

    public GetMessagesReply(Reply.Status status, String detailMessage) {
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
