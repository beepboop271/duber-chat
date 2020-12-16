package messages;

import java.io.Serializable;

/**
 * An object sent between the client and server. Contains
 * request data or response data, as well as methods to handle
 * the request on the server.
 *
 * @author Kevin Qiao
 */
public abstract class Message implements Serializable {
  private static final long serialVersionUID = 0L;
  
}
