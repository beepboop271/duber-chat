package messages;

public class CreateFriendRequest extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private String targetUsername;

  public CreateFriendRequest(String targetUsername) {
    this.targetUsername = targetUsername;
  }
}
