package messages;

public class GetMessages {
  private long chatID;
  private long beforeMessageId;
  private long amount;
  public GetMessages(long chatID, long beforeMessageId, long amount){
    this.chatID = chatID;
    this.beforeMessageId = beforeMessageId;
    this.amount = amount;
  }
}
