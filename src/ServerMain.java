import java.io.IOException;
import java.net.ServerSocket;

import logger.Log;
import reduber.ReDuber;
import server.Server;

/**
 * Launches the Duber Chat server.
 */
public class ServerMain {
  public static void main(String[] args) {
    try {
      Log.info("Starting server", "Server");
      new Thread(new Server(
        new ServerSocket(5000),
        new ReDuber()
      )).start();
    } catch (IOException e) {
      Log.error("Failed to open server socket", "Server", e);
    }
  }
}
