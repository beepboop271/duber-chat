package messages;

public class DoStatusChange extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private String userStatus;
  private String userMessage;

  public DoStatusChange(String status, String message) {
    this.userMessage = message;
    this.userStatus = status;
  }
}
