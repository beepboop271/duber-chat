package client;

public class ListOfFriendID {
  private long[] userIDs;
  private FriendInformation[] friendInfo;
  ListOfFriendID(long[] userIDs, FriendInformation[] friendInfo) {
    this.userIDs = userIDs;
    this.friendInfo = friendInfo;
  }

  public long[] getUserIDs(){
    return userIDs;
  }

  public FriendInformation[] getFriendInfo(){
    return friendInfo;
  }
}
