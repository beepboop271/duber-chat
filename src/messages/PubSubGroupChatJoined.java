package messages;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;

/**
 * A server-published message which indicates a user has
 * joined a group chat.
 *
 * @author Kevin Qiao
 */
public class PubSubGroupChatJoined extends PubSubMessage {
  private static final long serialVersionUID = 0L;

  private final long chatId;
  private final Long[] userIds;
  private final String name;
  private final long lastMessageId;

  public PubSubGroupChatJoined(
    long chatId,
    Long[] userIds,
    String name,
    long lastMessageId
  ) {
    this.chatId = chatId;
    this.userIds = userIds;
    this.name = name;
    this.lastMessageId = lastMessageId;
  }

  @Override
  public String toString() {
    return "PubSubGroupChatJoined[chatId="
      +this.chatId
      +", name="
      +this.name
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

  public String getName() {
    return this.name;
  }

  public long getLastMessageId() {
    return this.lastMessageId;
  }
}
