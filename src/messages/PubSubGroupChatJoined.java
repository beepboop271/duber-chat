package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;

public class PubSubGroupChatJoined extends PubSubMessage {
  private static final long serialVersionUID = 0L;

  private final long chatId;
  private Long[] userIds;
  private String name;
  private Long lastMessageId;

  public PubSubGroupChatJoined(
    long chatId,
    Long[] userIds,
    String name,
    Long lastMessageId
  ) {
    this.chatId = chatId;
    this.userIds = userIds;
    this.name = name;
    this.lastMessageId = lastMessageId;
  }

  @Override
  public void execute(ReDuber db) throws InterruptedException {
    try {
      if (this.userIds == null) {
        this.userIds = db.setGet("chats."+this.chatId+".members").get();
      }
      if (this.name == null) {
        this.name = db.stringGet("chats."+this.chatId+".name").get();
      }
      if (this.lastMessageId == null) {
        Long[] messages = db.listGetLast("chats."+this.chatId+".messages", 1).get();
        if (messages.length > 0) {
          this.lastMessageId = messages[0];
        }
      }
      db.publishMany("chats."+this.chatId+".members", this).get();
    } catch (ExecutionException e) {
      Log.warn("Failed to submit Publish job",  "MessagePublisher", this, e);
    }
  }
}
