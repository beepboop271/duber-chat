package client;

public class FriendRequestInformation {
  private long[] friendRequestIds;
  private String[] sourceUsernames;
  private String[] targetUsernames;
  FriendRequestInformation(long[] friendRequestIds, String[] sourceUsernames, String[] targetUsernames){
    this.friendRequestIds = friendRequestIds;
    this.sourceUsernames = sourceUsernames;
    this.targetUsernames = targetUsernames;
  }
  public long[] getFriendRequestIds(){
    return friendRequestIds;
  }
  public String[] getSourceUsernames(){
    return sourceUsernames;
  }
  public String[] getTargetUsernames(){
    return targetUsernames;
  }
}
