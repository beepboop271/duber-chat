package messages;

import reduber.ReDuber;
import server.ConnectedUser;

public class CreateGroupChat extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private long[] userIds;
  private String name;

  public CreateGroupChat(long[] userIds, String name) {
    this.userIds = userIds;
    this.name = name;
  }

  @Override
  public CommandReply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    // TODO Auto-generated method stub
    return null;
  }
}
