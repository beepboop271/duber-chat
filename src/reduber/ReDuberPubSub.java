package reduber;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ReDuberPubSub {
  private ExecutorService pool;

  ReDuberPubSub(int numThreads) {
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
    public void run() {
      synchronized (this.out) {
        try {
          this.out.writeObject(this.message);
          this.out.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
