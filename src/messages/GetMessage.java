package messages;

import reduber.ReDuber;
import server.ConnectedUser;

/**
 * A message for retrieval of certain data. Resembles an
 * HTTP GET, subclasses are prefixed with Get.
 *
 * @author Kevin Qiao
 */
public abstract class GetMessage extends Message {
  private static final long serialVersionUID = 0L;
  
  public abstract GetReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException;
}
