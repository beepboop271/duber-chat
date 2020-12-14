package server;

public class ConnectedUser {
  private boolean isLoggedIn;  
  private String username;
  private long userId;

  public ConnectedUser() {
    this.isLoggedIn = false;
  }

  public void login(String username, long userId) {
    if (!this.isLoggedIn) {
      this.username = username;
      this.userId = userId;
      this.isLoggedIn = true;
    }
  }

  public boolean isLoggedIn() {
    return this.isLoggedIn;
  }

  public String getUsername() {
    return this.username;
  }

  public long getUserId() {
    return this.userId;
  }
}
