package messages;

public abstract class GetReply extends Reply {
  private static final long serialVersionUID = 0L;

  public GetReply(Status status, String detailMessage) {
    super(status, detailMessage);
  }

  public GetReply(Status status) {
    super(status);
  }
}
