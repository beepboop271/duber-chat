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
  public void addChat(String chatName, long chatID, ChatInformation newChatInfo){
    String[] newChatNames = new String[chatNames.length + 1];
    long[] newChatIDs = new long[chatNames.length + 1];
    ChatInformation[] newChatInfoArray = new ChatInformation[chatNames.length + 1];
    for (int i = 0; i < chatNames.length; i++){
      newChatNames[i] = chatNames[i];
      newChatIDs[i] = chatIDs[i];
      newChatInfoArray[i] = chatInfo[i];
    }
    newChatNames[chatNames.length] = chatName;
    newChatIDs[chatNames.length] = chatID;
    newChatInfoArray[chatNames.length] = newChatInfo;

    this.chatNames = newChatNames;
    this.chatIDs = newChatIDs;
    this.chatInfo = newChatInfoArray;

  }
}
