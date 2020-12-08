package messages;

public class DoFriendCancel extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long friendRequestId;

  public DoFriendCancel(long friendRequestId) {
    this.friendRequestId = friendRequestId;
  }
}
