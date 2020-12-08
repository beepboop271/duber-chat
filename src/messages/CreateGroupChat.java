package messages;

public class CreateGroupChat extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long[] userIds;
  private String name;

  public CreateGroupChat(long[] userIds, String name) {
    this.userIds = userIds;
    this.name = name;
  }
}
