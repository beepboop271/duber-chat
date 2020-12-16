package messages;

/**
 * A reply to a GetMessage, containing the data requested.
 *
 * @author Kevin Qiao
 */
public abstract class GetReply extends Reply {
  private static final long serialVersionUID = 0L;

  public GetReply(Reply.Status status, String detailMessage) {
    super(status, detailMessage);
  }

  public GetReply(Reply.Status status) {
    super(status);
  }
}
