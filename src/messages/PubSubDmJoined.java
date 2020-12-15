package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;

public class PubSubDmJoined extends PubSubDirectMessage {
  private static final long serialVersionUID = 0L;

  private final long chatId;
  private final long userId;
  private final long lastMessageId;

  public PubSubDmJoined(long chatId, long userId, long lastMessageId) {
    this.chatId = chatId;
    this.userId = userId;
    this.lastMessageId = lastMessageId;
  }

  @Override
  public String toString() {
    return "PubSubDmJoined[chatId="
      +this.chatId
      +", userId="
      +this.userId
      +", lastMessageId="
      +this.lastMessageId
      +"]";
  }

  @Override
  public void execute(ReDuber db, long recipient) throws InterruptedException {
    try {
      db.publishDirect(recipient, this).get();
    } catch (ExecutionException e) {
      Log.warn("Failed to submit Publish job", "MessagePublisher", this, e);
    }
  }

  public long getChatId() {
    return this.chatId;
  }

  public long getUserId() {
    return this.userId;
  }

  public long getLastMessageId() {
    return this.lastMessageId;
  }
}
