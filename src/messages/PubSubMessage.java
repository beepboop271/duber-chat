package messages;

import reduber.ReDuber;

/**
 * A server-published message that is sent to some users,
 * the IDs of which are stored in the database somewhere.
 *
 * @author Kevin Qiao
 */
public abstract class PubSubMessage extends Message {
  private static final long serialVersionUID = 0L;
  
  public abstract void execute(ReDuber db) throws InterruptedException;
}
