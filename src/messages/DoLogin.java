package messages;

import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import server.ConnectedUser;

public class DoLogin extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private String username;
  private String password;

  public DoLogin(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return "DoLogin[password="+password+", username="+username+"]";
  }

  @Override
  public CommandReply execute(
    ReDuber db,
    ConnectedUser user
  ) throws InterruptedException {
    if (user.isLoggedIn()) {
      return CommandReply.badOperation("User already logged in");
    }

    try {
      String password = db.stringGet("users."+this.username+".password").get();

      if (password == null) {
        return CommandReply.notExists("Username does not exist");
      } else if (password.equals(this.password)) {
        long userId = db.longGet("users."+this.username+".id").get();

        user.login(this.username, userId);
        new PubSubStatusChange(userId, "ONLINE", null).execute(db);
        
        return CommandReply.ok();
      } else {
        return CommandReply.badArgs("Password is incorrect");
      }
    } catch (ExecutionException e) {
      Log.error("Failed to login", "MessageHandler", this, e);
    }
    return CommandReply.serverUnknown();
  }
}
