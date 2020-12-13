package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public class CreateDm extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long userId;

  public CreateDm(long userId) {
    this.userId = userId;
  }

  @Override
  public CommandReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    // TODO Auto-generated method stub
    return null;
  }
}
