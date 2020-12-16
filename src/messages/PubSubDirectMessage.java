package messages;

import reduber.ReDuber;

/**
 * A server-published message which is sent to a specific
 * user.
 *
 * @author Kevin Qiao
 */
public abstract class PubSubDirectMessage extends Message {
  private static final long serialVersionUID = 0L;

  public abstract void execute(ReDuber db, long recipient)
    throws InterruptedException;
}
