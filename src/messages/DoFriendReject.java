package messages;

public class DoFriendReject extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long friendRequestId;

  public DoFriendReject(long friendRequestId) {
    this.friendRequestId = friendRequestId;
  }
}
