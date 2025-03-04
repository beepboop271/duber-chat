package client;

public class ListOfFriendID {
  private long[] userIDs;
  private FriendInformation[] friendInfo;
  ListOfFriendID(long[] userIDs, FriendInformation[] friendInfo) {
    this.userIDs = userIDs;
    this.friendInfo = friendInfo;
  }
  //getters
  public long[] getUserIDs(){
    return userIDs;
  }
  public FriendInformation[] getFriendInfo(){
    return friendInfo;
  }
  //removes a friend
  public void removeFriend(long ID){
    int index = -1;
    for (int i = 0; i < userIDs.length; i++){
      if (ID == userIDs[i]){
        index = i;
      }
    }
    if (index != -1){//user exists in the frined list
      long[] newUserIDs = new long[userIDs.length - 1];//new array one smaller
      FriendInformation[] newFriendInfo = new FriendInformation[newUserIDs.length];
      for (int i = 0; i < userIDs.length; i++){
        if (i < index){//adds all ids before the index
          newUserIDs[i] = userIDs[i];
          newFriendInfo[i] = friendInfo[i];
        } else if (i == index) {//ignores the id of the index

        } else {//takes the remaining ids and fills the index gap
          newUserIDs[i - 1] = userIDs[i];
          newFriendInfo[i - 1] = friendInfo[i];
        }

      }
      //reassigns the new IDs into the object
      System.out.println("removed from friend req: " + ID);
      this.userIDs = newUserIDs;
      this.friendInfo = newFriendInfo;
    }
  }
  //adds a friend
  public void addFriend(long newID, FriendInformation newFriend){
    long[] newUserIDs = new long[userIDs.length + 1];//new array one larger
    FriendInformation[] newFriendInfo = new FriendInformation[newUserIDs.length];
    for (int i = 0; i < userIDs.length; i++){

      newUserIDs[i] = userIDs[i];
      newFriendInfo[i] = friendInfo[i];
    }
    //adds the new friend at the end of the array
    newUserIDs[newUserIDs.length - 1] = newID;
    newFriendInfo[newUserIDs.length - 1] = newFriend;

    //reassigns the new IDs into the object
    this.userIDs = newUserIDs;
    this.friendInfo = newFriendInfo;
    System.out.println("updated friends panel");
  }
  
}
