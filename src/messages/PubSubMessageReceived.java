package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;

/**
 * A server-published message which indicates a new message
 * has been sent in a chat.
 *
 * @author Kevin Qiao
 */
public class PubSubMessageReceived extends PubSubMessage {
  private static final long serialVersionUID = 0L;

  private final long messageId;
  private final long chatId;
  private final long userId;
  private final long time;
  private final String message;

  public PubSubMessageReceived(
    long messageId,
    long chatId,
    long userId,
    long time,
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
    return "PubSubMessageReceived[chatId="
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
      db.publishMany("chats."+this.chatId+".members", this).get();
    } catch (ExecutionException e) {
      Log.warn("Failed to submit Publish job", "MessagePublisher", this, e);
    }
  }

  public long getMessageId() {
    return this.messageId;
  }

  public long getChatId() {
    return this.chatId;
  }

  public long getUserId() {
    return this.userId;
  }

  public long getTime() {
    return this.time;
  }

  public String getMessage() {
    return this.message;
  }
}
