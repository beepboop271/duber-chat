package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import logger.Log;
import reduber.ReDuber;

public class Server implements Runnable {
  private final ServerSocket sock;
  private final ReDuber db;

  public Server(ServerSocket sock, ReDuber db) {
    this.sock = sock;
    this.db = db;
  }

  @Override
  public void run() {
    while (true) {
      Socket clientSock;
      try {
        clientSock = this.sock.accept();
      } catch (IOException e) {
        Log.warn("Server", "Failed to accept connection", e);
        break;
      }
      new Thread(new ClientHandler(clientSock, db)).start();
    }

    try {
      this.sock.close();
    } catch (IOException e) {
      Log.warn("Server", "Failed to close socket", e);
    }
  }
}
