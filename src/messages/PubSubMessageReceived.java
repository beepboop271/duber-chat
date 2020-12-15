package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;

public class PubSubMessageReceived extends PubSubMessage {
  private static final long serialVersionUID = 0L;

  private long messageId;
  private Long chatId;
  private Long userId;
  private Long time;
  private String message;

  public PubSubMessageReceived(
    long messageId,
    Long chatId,
    Long userId,
    Long time,
    String message
  ) {
    this.messageId = messageId;
    this.chatId = chatId;
    this.userId = userId;
    this.time = time;
    this.message = message;
  }

  @Override
  public String toString() {
    return "PubSubMessageReceived [chatId="
      +this.chatId
      +", message="
      +this.message
      +", userId="
      +this.userId
      +", messageId="
      +this.messageId
      +", time="
      +this.time
      +"]";
  }

  @Override
  public void execute(ReDuber db) throws InterruptedException {
    try {
      if (this.chatId == null) {
        this.chatId = db.longGet("messages."+this.messageId+".chat").get();
      }
      if (this.userId == null) {
        this.userId = db.longGet("messages."+this.messageId+".author").get();
      }
      if (this.time == null) {
        this.time = db.longGet("messages."+this.messageId+".time").get();
      }
      if (this.message == null) {
        this.message =
          db.stringGet("messages."+this.messageId+".message").get();
      }
      db.publishMany("chats."+this.chatId+".members", this).get();
    } catch (ExecutionException e) {
      Log.warn("Failed to submit Publish job", "MessagePublisher", this, e);
    }
  }

  public long getMessageId() {
    return this.messageId;
  }

  public Long getChatId() {
    return this.chatId;
  }

  public Long getUserId() {
    return this.userId;
  }

  public Long getTime() {
    return this.time;
  }

  public String getMessage() {
    return this.message;
  }
}
