package messages;

public class CreateDm extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long userId;

  public CreateDm(long userId) {
    this.userId = userId;
  }
}
