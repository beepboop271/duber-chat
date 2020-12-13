package client;

public class FriendInformation {
  private long[] friendIDs;
  private String[] friendUsernames;
  private String[] friendStatuses;
  private long[] chatIDs;
  private ChatInformation[] chatInfo;
  FriendInformation(long[] friendIDs, String[] friendUsernames, String[] friendStatuses, long[] chatIDs, ChatInformation[] chatInfo){
    this.friendIDs = friendIDs;
    this.friendUsernames = friendUsernames;
    this.friendStatuses = friendStatuses;
    this.chatIDs = chatIDs;
    this.chatInfo = chatInfo;
  }
  public long[] getFriendIDs(){
    return friendIDs;
  }
  public String[] getFriendUsernames(){
    return friendUsernames;
  }
  public String[] getFriendStatuses(){
    return friendStatuses;
  }
  public long[] getChatIDs(){
    return chatIDs;
  }
  public ChatInformation[] getChatInfo(){
    return chatInfo;
  }
}
