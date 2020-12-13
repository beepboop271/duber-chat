package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public class CreateMessage extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long chatId;
  private String message;

  public CreateMessage(long chatId, String message) {
    this.chatId = chatId;
    this.message = message;
  }

  @Override
  public CommandReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    // TODO Auto-generated method stub
    if (!user.isLoggedIn()) {
      return CommandReply.noPermission("Not logged in");
    }
    return null;
  }
}
