package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public abstract class GetMessage extends Message {
  private static final long serialVersionUID = 0L;
  
  public abstract GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException;
}
