package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

public class DoPubSubLogin extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private String username;
  private String password;

  public DoPubSubLogin(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return "DoPubSubLogin[username="
      +this.username
      +", password="
      +this.password
      +"]";
  }

  @Override
  public Reply execute(ReDuber db, ConnectedUser user)
    throws InterruptedException {
    if (user.isLoggedIn()) {
      return Reply.badOperation("User already logged in PubSub");
    }

    try {
      String password = db.stringGet("users."+this.username+".password").get();

      if (password == null) {
        return Reply.notExists("Username does not exist");
      } else if (password.equals(this.password)) {
        long userId = db.longGet("users."+this.username+".id").get();

        user.login(this.username, userId);
        return Reply.ok();
      } else {
        return Reply.badArgs("Password is incorrect");
      }
    } catch (ExecutionException e) {
      Log.error("Failed to login PubSub", "MessageHandler", this, e);
    }

    return Reply.serverUnknown();
  }
}
