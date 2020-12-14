package messages;

import reduber.ReDuber;

public abstract class PubSubDirectMessage extends Message {
  private static final long serialVersionUID = 0L;

  public abstract void execute(ReDuber db, long recipient)
    throws InterruptedException;
}
