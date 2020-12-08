package messages;

public class DoFriendAccept extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long friendRequestId;

  public DoFriendAccept(long friendRequestId) {
    this.friendRequestId = friendRequestId;
  }
}
