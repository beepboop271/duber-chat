package messages;
public class DoStatusChange {
    String userStatus;
    String userMessage;
    DoStatusChange(String status, String message){
        this.userMessage = message;
        this.userStatus = status;
    }
}
