package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public abstract class CommandMessage extends Message {
  private static final long serialVersionUID = 0L;

  public abstract CommandReply execute(
    ReDuber db,
    ConnectedUser user
  ) throws InterruptedException;
}
