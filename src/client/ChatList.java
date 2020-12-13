package client;

public class ChatList {
  private long[] friendIDs;
  private String[] friendUsernames;
  private String[] chatNames;
  private long[] chatIDs;
  private ChatInformation[] chatInfo;
  ChatList(long[] friendIDs, String[] friendUsernames, String[] chatNames, long[] chatIDs, ChatInformation[] chatInfo){
    this.friendIDs = friendIDs;
    this.friendUsernames = friendUsernames;
    this.chatNames = chatNames;
    this.chatIDs = chatIDs;
    this.chatInfo = chatInfo;
  }
  public long[] getFriendIDs(){
    return friendIDs;
  }
  public String[] getFriendUsernames(){
    return friendUsernames;
  }
  public String[] getChatNames(){
    return chatNames;
  }
  public long[] getChatIDs(){
    return chatIDs;
  }
  public ChatInformation[] getChatInfo(){
    return chatInfo;
  }
}
