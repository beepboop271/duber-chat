package reduber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReDuberPubSub {
  private ExecutorService pool;

  public ReDuberPubSub(int numThreads) {
    this.pool = Executors.newFixedThreadPool(numThreads);
  }
}
