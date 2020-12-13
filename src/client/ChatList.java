package client;

public class ChatList {
  private String[] chatNames;
  private long[] chatIDs;
  private ChatInformation[] chatInfo;
  ChatList(String[] chatNames, long[] chatIDs, ChatInformation[] chatInfo){
    this.chatNames = chatNames;
    this.chatIDs = chatIDs;
    this.chatInfo = chatInfo;
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
