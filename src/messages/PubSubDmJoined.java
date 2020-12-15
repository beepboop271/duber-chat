package messages;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;

public class PubSubDmJoined extends PubSubMessage {
  private static final long serialVersionUID = 0L;

  private final long chatId;
  private final Long[] userIds;
  private final long lastMessageId;

  public PubSubDmJoined(long chatId, Long[] userIds, long lastMessageId) {
    this.chatId = chatId;
    this.userIds = userIds;
    this.lastMessageId = lastMessageId;
  }

  @Override
  public String toString() {
    return "PubSubDmJoined[chatId="
      +this.chatId
      +", userIds="
      +Arrays.toString(this.userIds)
      +", lastMessageId="
      +this.lastMessageId
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

  public long getChatId() {
    return this.chatId;
  }

  public Long[] getUserIds() {
    return this.userIds;
  }

  public long getLastMessageId() {
    return this.lastMessageId;
  }
}
