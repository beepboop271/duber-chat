package messages;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import logger.Log;
import reduber.ReDuber;
import reduber.ReDuberId;
import server.ConnectedUser;

public class CreateAccount extends CommandMessage {
  private static final long serialVersionUID = 0L;

  private String username;
  private String password;

  public CreateAccount(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return "CreateAccount[password="+password+", username="+username+"]";
  }

  @Override
  public CommandReply execute(
    ReDuber db,
    ConnectedUser user
  ) throws InterruptedException {
    try {
      // plain text passwords lol
      String key = "users."+this.username+".password";
      ReDuber.Status result = db.setNotExists(key, this.password).get();

      if (result == ReDuber.Status.OK) {
        // no atomic batch operations lol
        long userId = ReDuberId.getId();
        CompletableFuture.allOf(
          db.set("users."+this.username+".id", userId),
          db.set("users."+userId+".username", this.username),
          db.set("users."+userId+".status", "OFFLINE"),
          db.set("users."+userId+".message", "")
        ).join();
        return CommandReply.ok();
      } else if (result == ReDuber.Status.NO_CHANGE) {
        return CommandReply.exists("Username is taken");
      }
    } catch (ExecutionException e) {
      Log.error("Failed to create account", "MessageHandler", this, e);
    }
    return CommandReply.serverUnknown();
  }
}
