package messages;

public class DoLogin extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private String username;
  private String password;

  public DoLogin(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
