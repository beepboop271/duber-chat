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
  //getters
  public long[] getFriendRequestIds(){
    return friendRequestIds;
  }
  public String[] getSourceUsernames(){
    return sourceUsernames;
  }
  public String[] getTargetUsernames(){
    return targetUsernames;
  }
  //adding a new friend request
  public void addRequest(long requestID, String sourceUsername, String targetUsername){
    long[] newFriendRequestIds = new long[friendRequestIds.length + 1];
    String[] newSourceUsernames = new String[friendRequestIds.length + 1];
    String[] newTargetUsernames = new String[friendRequestIds.length + 1];
    for (int i = 0; i < friendRequestIds.length; i++){
      newFriendRequestIds[i] = friendRequestIds[i];
      newSourceUsernames[i] = sourceUsernames[i];
      newTargetUsernames[i] = targetUsernames[i];
    }
    newFriendRequestIds[friendRequestIds.length] = requestID;
    newSourceUsernames[friendRequestIds.length] = sourceUsername;
    newTargetUsernames[friendRequestIds.length] = targetUsername;
    this.friendRequestIds = newFriendRequestIds;
    this.sourceUsernames = newSourceUsernames;
    this.targetUsernames = newTargetUsernames;
  }
  //two import ways of removing a friend request
  public void removeRequest(long requestID){
    int index = -1;
    for(int i = 0; i < friendRequestIds.length; i++){
      if (requestID == friendRequestIds[i]){
        index = i;
      }
    }
    if (index != -1){//ID exists in the frined list
      long[] newFriendRequestIds = new long[friendRequestIds.length - 1];
      String[] newSourceUsernames = new String[friendRequestIds.length - 1];
      String[] newTargetUsernames = new String[friendRequestIds.length - 1];
      for (int i = 0; i < friendRequestIds.length; i++){
        if (i < index){//adds all ids before the index
          newFriendRequestIds[i] = friendRequestIds[i];
          newSourceUsernames[i] = sourceUsernames[i];
          newTargetUsernames[i] = newTargetUsernames[i];
        } else if (i == index) {//ignores the id of the index

        } else {//takes the remaining ids and fills the index gap
          newFriendRequestIds[i - 1] = friendRequestIds[i];
          newSourceUsernames[i - 1] = sourceUsernames[i];
          newTargetUsernames[i - 1] = newTargetUsernames[i];
        }

      }
      this.friendRequestIds = newFriendRequestIds;
      this.sourceUsernames = newSourceUsernames;
      this.targetUsernames = newTargetUsernames;
    }
  }
  public void removeRequest(String userID, String username){//based on the user and recipient
    int index = -1;
    for(int i = 0; i < friendRequestIds.length; i++){
      if (userID.equals(sourceUsernames[i]) && username.equals(targetUsernames[i])) {//recipient sent the friend request
        index = i;
      } else if (userID.equals(targetUsernames[i]) && username.equals(sourceUsernames[i])) {//client sent the friend request
        index = i;
      }
    }
    if (index != -1){//ID exists in the frined list
      long[] newFriendRequestIds = new long[friendRequestIds.length - 1];
      String[] newSourceUsernames = new String[friendRequestIds.length - 1];
      String[] newTargetUsernames = new String[friendRequestIds.length - 1];
      for (int i = 0; i < friendRequestIds.length; i++){
        if (i < index){//adds all ids before the index
          newFriendRequestIds[i] = friendRequestIds[i];
          newSourceUsernames[i] = sourceUsernames[i];
          newTargetUsernames[i] = newTargetUsernames[i];
        } else if (i == index) {//ignores the id of the index

        } else {//takes the remaining ids and fills the index gap
          newFriendRequestIds[i - 1] = friendRequestIds[i];
          newSourceUsernames[i - 1] = sourceUsernames[i];
          newTargetUsernames[i - 1] = newTargetUsernames[i];
        }

      }
      this.friendRequestIds = newFriendRequestIds;
      this.sourceUsernames = newSourceUsernames;
      this.targetUsernames = newTargetUsernames;
    }
  }
  
}
