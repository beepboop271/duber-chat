package messages;

import reduber.ReDuber;
import server.ConnectedUser;

/**
 * A Message which represents an action to be done on the
 * server. Resembles an HTTP POST, subclasses are prefiexed
 * with Do or Create.
 *
 * @author Kevin Qiao
 */
public abstract class CommandMessage extends Message {
  private static final long serialVersionUID = 0L;

  public abstract Reply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException;
}
