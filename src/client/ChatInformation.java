package client;

public class ChatInformation {
  private long[] messageIDs;
  private long[] authorIDs;
  private long[] times;
  private String[] messages;
  ChatInformation(long[] messageIDs, long[] authorIDs, long[] times, String[] messages) {
    this.messageIDs = messageIDs;
    this.authorIDs = authorIDs;
    this.times = times;
    this.messages = messages;
  }
  public long[] getMessageIDs(){
    return messageIDs;
  }
  public long[] getAuthorIDs(){
    return authorIDs;
  }
  public long[] getTimes(){
    return times;
  }
  public String[] getMessages(){
    return messages;
  }
}
