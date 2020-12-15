package client;

public class ChatInformation {
  private long[] messageIDs;
  private long[] userIDs;
  private long[] times;
  private String[] messages;
  ChatInformation(long[] messageIDs, long[] userIDs, long[] times, String[] messages) {
    this.messageIDs = messageIDs;
    this.userIDs = userIDs;
    this.times = times;
    this.messages = messages;
  }
  public long[] getMessageIDs(){
    return messageIDs;
  }
  public long[] getUserIDs(){
    return userIDs;
  }
  public long[] getTimes(){
    return times;
  }
  public String[] getMessages(){
    return messages;
  }
  public void addMessage(long messageID, long userID, long time, String message){
    long[] newMessageIDs = new long[messages.length + 1];
    long[] newUserIDs = new long[messages.length + 1];
    long[] newTimes = new long[messages.length + 1];
    String[] newMessages = new String[messages.length + 1];
    for (int i = 0; i < messages.length; i++) {
      newMessageIDs[i] = messageIDs[i];
      newUserIDs[i] = userIDs[i];
      newTimes[i] = times[i];
      newMessages[i] = messages[i];
    }
    newMessageIDs[messages.length + 1] = messageID;
    newUserIDs[messages.length + 1] = userID;
    newTimes[messages.length + 1] = time;
    newMessages[messages.length + 1] = message;

    this.messageIDs = newMessageIDs;
    this.userIDs = newUserIDs;
    this.times = newTimes;
    this.messages = newMessages;
  }
}
