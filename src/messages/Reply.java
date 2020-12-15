package messages;

public class Reply extends Message {
  private static final long serialVersionUID = 0L;

  public enum Status {
    OK,
    E_EXISTS,
    E_NOT_EXISTS,
    E_BAD_ARGS,
    E_BAD_OPERATION,
    E_NO_PERMISSION,
    E_SERVER_UNKNOWN,
  }

  private final Status status;
  private final String detailMessage;

  public Reply(Status status, String detailMessage) {
    this.status = status;
    this.detailMessage = detailMessage;
  }

  public Reply(Status status) {
    this(status, "");
  }

  @Override
  public String toString() {
    return "Reply[status="
      +this.status
      +", detailMessage="
      +this.detailMessage
      +"]";
  }

  public Status getStatus() {
    return this.status;
  }

  public String getDetailMessage() {
    return this.detailMessage;
  }

  // static methods to make creating replies shorter

  public static Reply ok() {
    return new Reply(Status.OK);
  }

  public static Reply exists(String detailMessage) {
    return new Reply(Status.E_EXISTS, detailMessage);
  }

  public static Reply notExists(String detailMessage) {
    return new Reply(Status.E_NOT_EXISTS, detailMessage);
  }

  public static Reply badArgs(String detailMessage) {
    return new Reply(Status.E_BAD_ARGS, detailMessage);
  }

  public static Reply badOperation(String detailMessage) {
    return new Reply(Status.E_BAD_OPERATION, detailMessage);
  }

  public static Reply noPermission(String detailMessage) {
    return new Reply(Status.E_NO_PERMISSION, detailMessage);
  }

  public static Reply serverUnknown() {
    return new Reply(Status.E_SERVER_UNKNOWN);
  }
}
