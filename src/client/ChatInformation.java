package client;

public class ChatInformation {
  private long[] messageIDs;
  private String[] userIDs;
  private long[] times;
  private String[] messages;
  ChatInformation(long[] messageIDs, String[] userIDs, long[] times, String[] messages) {
    this.messageIDs = messageIDs;
    this.userIDs = userIDs;
    this.times = times;
    this.messages = messages;
  }
  public long[] getMessageIDs(){
    return messageIDs;
  }
  public String[] getUserIDs(){
    return userIDs;
  }
  public long[] getTimes(){
    return times;
  }
  public String[] getMessages(){
    return messages;
  }
}
