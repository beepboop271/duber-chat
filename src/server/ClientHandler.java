package server;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import logger.Log;
import messages.CommandMessage;
import messages.DoLogin;
import messages.DoPubSubLogin;
import messages.GetMessage;
import messages.GetReply;
import messages.Reply;
import reduber.ReDuber;
import reduber.ReDuberId;

class ClientHandler implements Runnable {
  private final Socket sock;
  private ObjectOutputStream out;
  private ObjectInputStream in;

  private final ReDuber db;
  private final long sessionId;
  private final ConnectedUser user;

  ClientHandler(Socket sock, ReDuber db) {
    this.sock = sock;
    this.db = db;
    this.sessionId = ReDuberId.getId();
    this.user = new ConnectedUser();
    Log.info("Received new client", "ClientHandler", this.sessionId);
  }

  private void destroy() {
    Log.info("Shutting down", "ClientHandler", this.sessionId);
    this.close(this.out);
    this.close(this.in);
    this.close(this.sock);
  }

  private void close(Closeable o) {
    if (o != null) {
      try {
        o.close();
      } catch (IOException e) {
        Log.warn("Failed to close", "ClientHandler", this.sessionId, o, e);
      }
    }
  }

  @Override
  public void run() {
    try {
      this.out = new ObjectOutputStream(this.sock.getOutputStream());
      this.in = new ObjectInputStream(this.sock.getInputStream());
    } catch (IOException e) {
      Log.warn("Failed to create IO streams", "ClientHandler", this.sessionId, e);
      this.destroy();
      return;
    }

    while (true) {
      if (Thread.interrupted()) {
        Log.error("Loop interrupted", "ClientHandler", this.sessionId);
        this.destroy();
        return;
      }

      Object o;
      try {
        o = this.in.readObject();
      } catch (IOException | ClassNotFoundException e) {
        Log.warn("Client data read failed", "ClientHandler", this.sessionId, e);
        this.destroy();
        return;
      }

      Log.info("Recieved message", "ClientHandler", this.sessionId, o);

      try {
        if (o instanceof CommandMessage) {
          Reply reply = ((CommandMessage)o).execute(this.db, this.user);
          if (o instanceof DoLogin) {
            if (this.user.isLoggedIn()) {
              Log.info("Logged in", "ClientHandler", this.sessionId, o);
            }
          } else if (o instanceof DoPubSubLogin) {
            if (this.user.isLoggedIn()) {
              this.out.writeObject(reply);
              this.runPubSub((DoPubSubLogin)o);
              this.destroy();
              return;
            }
          }
          Log.info("Sending command reply", "ClientHandler", this.sessionId, reply);
          this.out.writeObject(reply);
        } else if (o instanceof GetMessage) {
          GetReply reply = ((GetMessage)o).execute(this.db, this.user);
          Log.info("Sending get reply", "ClientHandler", this.sessionId, reply);
          this.out.writeObject(reply);
        }
      } catch (IOException e) {
        Log.warn("Client data reply failed", "ClientHandler", this.sessionId, e);
        this.destroy();
        return;
      } catch (InterruptedException e) {
        Log.error("Client message processing interrupted", "ClientHandler", this.sessionId, e);
        this.destroy();
        return;
      }
    }
  }

  private void runPubSub(DoPubSubLogin command) {
    try {
      this.db.pubSubLogin(this.user.getUserId(), this.out).get();
    } catch (InterruptedException e) {
      Log.error("PubSub login interrupted", "ClientPublishHandler", this.sessionId, e);
      return;
    } catch (ExecutionException e) {
      Log.error("PubSub login failed", "ClientPublishHandler", this.sessionId, e);
      return;
    }
    Log.info("Logged in PubSub", "ClientPublishHandler", this.sessionId, command);
    while (true) {
      try {
        Thread.sleep(30000);
      } catch (InterruptedException e) {
        Log.error("PubSub loop interrupted", "ClientPublishHandler", this.sessionId, e);
        return;
      }
    }
  }
}
