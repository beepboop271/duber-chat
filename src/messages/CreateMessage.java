package messages;
public class CreateMessage {
    long chatID;
    String message;
    CreateMessage(long ID, String msg){
        this.chatID = ID;
        this.message = msg;
    }
}
