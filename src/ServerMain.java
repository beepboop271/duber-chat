import java.io.IOException;
import java.net.ServerSocket;

import logger.Log;
import reduber.ReDuber;
import server.Server;

public class ServerMain {
  public static void main(String[] args) {
    try {
      Log.info("Starting server", "Server");
      new Thread(new Server(
        new ServerSocket(41047),
        new ReDuber()
      )).start();
    } catch (IOException e) {
      Log.error("Failed to open server socket", "Server", e);
    }
  }
}
