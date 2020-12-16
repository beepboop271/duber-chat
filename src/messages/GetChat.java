package messages;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

/**
 * A message for retrieval of data for a specific chat.
 *
 * @author Kevin Qiao
 */
public class GetChat extends GetMessage {
  private static final long serialVersionUID = 0L;

  private final long chatId;

  public GetChat(long chatId) {
    this.chatId = chatId;
  }

  @Override
  public String toString() {
    return "GetChat[chatId="+this.chatId+"]";
  }

  @Override
  public GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return new GetChatReply(Reply.Status.E_NO_PERMISSION, "Not logged in");
    }

    try {
      String type = db.stringGet("chats."+this.chatId+".type").get();
      if (type == null) {
        return new GetChatReply(
          Reply.Status.E_NOT_EXISTS,
          "Chat does not exist"
        );
      }
      if (
        db.setContains("chats."+this.chatId+".members", user.getUserId()).get()
          != ReDuber.Status.TRUE
      ) {
        return new GetChatReply(
          Reply.Status.E_NO_PERMISSION,
          "Cannot get chat you are not in"
        );
      }
      Long[] users = db.setGet("chats."+this.chatId+".members").get();
      String name = db.stringGet("chats."+this.chatId+".name").get();
      Long lastMessage =
        db.listGetLast("chats."+this.chatId+".messages", 1).get()[0];
      return new GetChatReply(type, users, name, lastMessage);
    } catch (ExecutionException e) {
      Log.warn("Failed to get chat", "MessageHandler", this, e);
    }
    return new GetChatReply(Reply.Status.E_SERVER_UNKNOWN);
  }

  /**
   * A message containing requested data for a specific chat.
   *
   * @author Kevin Qiao
   */
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

    public GetChatReply(Reply.Status status) {
      super(status);
      this.type = null;
      this.userIds = null;
      this.name = null;
      this.lastMessageId = null;
    }

    public GetChatReply(Reply.Status status, String detailMessage) {
      super(status, detailMessage);
      this.type = null;
      this.userIds = null;
      this.name = null;
      this.lastMessageId = null;
    }

    @Override
    public String toString() {
      return "GetChatReply[name="
        +this.name
        +", type="
        +this.type
        +", userIds="
        +Arrays.toString(this.userIds)
        +", lastMessageId="
        +this.lastMessageId
        +"]";
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
