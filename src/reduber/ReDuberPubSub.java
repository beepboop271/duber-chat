package reduber;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import logger.Log;

class ReDuberPubSub {
  private ExecutorService pool;

  ReDuberPubSub(int numThreads) {
    Log.info("Starting ReDuberPubSub", "ReDuberPubSub");
    this.pool = Executors.newFixedThreadPool(numThreads);
  }

  void submit(Serializable message, ObjectOutputStream out) {
    this.pool.submit(new PublishJob(message, out));
  }

  private static class PublishJob implements Runnable {
    private Serializable message;
    private ObjectOutputStream out;

    private PublishJob(Serializable message, ObjectOutputStream out) {
      this.message = message;
      this.out = out;
    }

    @Override
    public String toString() {
      return "PublishJob[message="+this.message+", out="+this.out+"]";
    }

    @Override
    public void run() {
      synchronized (this.out) {
        try {
          this.out.writeObject(this.message);
          this.out.flush();
        } catch (IOException e) {
          Log.warn("Failed to deliver message", "ReDuberPubSub", this, e);
        }
      }
    }
  }
}
