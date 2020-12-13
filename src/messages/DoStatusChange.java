package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public class DoStatusChange extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private String userStatus;
  private String userMessage;

  public DoStatusChange(String status, String message) {
    this.userMessage = message;
    this.userStatus = status;
  }

  @Override
  public CommandReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    // TODO Auto-generated method stub
    return null;
  }
}
