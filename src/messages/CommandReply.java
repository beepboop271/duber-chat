package messages;

public class CommandReply extends Message {
  private static final long serialVersionUID = 0L;

  public enum Status {
    OK,
    E_EXISTS,
    E_NOT_EXISTS,
    E_BAD_ARGS,
    E_BAD_OPERATION,
    E_NO_PERMISSION,
  }

  private final Status status;
  private final String detailMessage;

  public CommandReply(Status status, String detailMessage) {
    this.status = status;
    this.detailMessage = detailMessage;
  }

  public CommandReply(Status status) {
    this(status, "");
  }

  public Status getStatus() {
    return this.status;
  }

  public String getDetailMessage() {
    return this.detailMessage;
  }

  // static methods to make creating replies shorter

  public static CommandReply ok() {
    return new CommandReply(Status.OK);
  }

  public static CommandReply exists(String detailMessage) {
    return new CommandReply(Status.E_EXISTS, detailMessage);
  }

  public static CommandReply notExists(String detailMessage) {
    return new CommandReply(Status.E_NOT_EXISTS, detailMessage);
  }

  public static CommandReply badArgs(String detailMessage) {
    return new CommandReply(Status.E_BAD_ARGS, detailMessage);
  }

  public static CommandReply badOperation(String detailMessage) {
    return new CommandReply(Status.E_BAD_OPERATION, detailMessage);
  }

  public static CommandReply noPermission(String detailMessage) {
    return new CommandReply(Status.E_NO_PERMISSION, detailMessage);
  }
}
