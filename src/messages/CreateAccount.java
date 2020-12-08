package messages;

public class CreateAccount extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private String username;
  private String password;

  public CreateAccount(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
