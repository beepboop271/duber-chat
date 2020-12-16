package client;

public class FriendInformation {
  private String username;
  private String status;
  private String usermessage;
  FriendInformation(String username, String status, String usermessage){
    this.username = username;
    this.status = status;
    this.usermessage = usermessage;
  }
  //getters
  public String getUsername(){
    return username;
  }
  public String getStatus(){
    return status;
  }
  public String getUserMessage(){
    return usermessage;
  }
}
