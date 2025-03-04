package messages;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import reduber.ReDuberId;
import server.ConnectedUser;

/**
 * A message which creates a group chat and adds members to
 * it.
 *
 * @author Kevin Qiao
 */
public class CreateGroupChat extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private Long[] userIds;
  private String name;

  public CreateGroupChat(Long[] userIds, String name) {
    this.userIds = userIds;
    this.name = name;
  }

  @Override
  public String toString() {
    return "CreateGroupChat[name="
      +this.name
      +", userIds="
      +Arrays.toString(this.userIds)
      +"]";
  }

  @Override
  public Reply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (!user.isLoggedIn()) {
      return Reply.noPermission("Not logged in");
    }

    try {
      for (long userId : this.userIds) {
        ReDuber.Status status = db.setContains(
          "users."+user.getUserId()+".friends",
          userId
        ).get();
        if (status == ReDuber.Status.FALSE) {
          return Reply.notExists(
            "User(s) does not exist or is not friends with you"
          );
        } else if (status != ReDuber.Status.TRUE) {
          Log.warn("Failed to create group chat", "MessageHandler", this);
          return Reply.serverUnknown();
        }
      }

      long chatId = ReDuberId.getId();
      long messageId = ReDuberId.getId();
      StringJoiner join = new StringJoiner(
        ", ",
        "<@"+user.getUserId()+"> added ",
        " to the chat"
      );

      for (long userId : this.userIds) {
        join.add("<@"+userId+">");
        CompletableFuture
          .allOf(
            db.setAdd("chats."+chatId+".members", userId),
            db.setAdd("users."+userId+".chats", chatId)
          )
          .get();
      }

      CompletableFuture
        .allOf(
          db.set("chats."+chatId+".type", "GROUP"),
          db.set("chats."+chatId+".name", this.name),
          db.set("messages."+messageId+".author", 0),
          db.set("messages."+messageId+".chat", chatId),
          db.set("messages."+messageId+".time", System.currentTimeMillis()),
          db.set("messages."+messageId+".message", join.toString()),
          db.listAdd("chats."+chatId+".messages", messageId)
        )
        .get();
      new PubSubGroupChatJoined(chatId, this.userIds, this.name, messageId)
        .execute(db);
      return Reply.ok();
    } catch (ExecutionException e) {
      Log.warn("Failed to create group chat", "MessageHandler", this, e);
    }
    return Reply.serverUnknown();
  }
}
