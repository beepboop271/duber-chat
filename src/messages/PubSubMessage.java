package messages;

import reduber.ReDuber;

public abstract class PubSubMessage extends Message {
  private static final long serialVersionUID = 0L;
  
  public abstract void execute(ReDuber db) throws InterruptedException;
}
