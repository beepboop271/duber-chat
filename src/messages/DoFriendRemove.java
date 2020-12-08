package messages;

public class DoFriendRemove extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long userId;

  public DoFriendRemove(long userId) {
    this.userId = userId;
  }
}
