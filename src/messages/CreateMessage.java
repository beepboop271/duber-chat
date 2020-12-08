package messages;

public class CreateMessage extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long chatId;
  private String message;

  public CreateMessage(long chatId, String message) {
    this.chatId = chatId;
    this.message = message;
  }
}
