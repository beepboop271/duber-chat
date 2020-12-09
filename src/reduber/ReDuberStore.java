package reduber;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ReDuberStore implements Runnable, Serializable {
  private static final long serialVersionUID = 0L;

  private transient BlockingQueue<Operation<?, ?, ?>> opQueue;

  private final HashMap<String, Long> longMap;
  private final HashMap<String, String> stringMap;
  private final HashMap<String, ArrayDeque<Long>> listMap;
  private final HashMap<String, HashSet<Long>> setMap;

  ReDuberStore(int queueSize, int initialCapacities) {
    this.opQueue = new ArrayBlockingQueue<>(queueSize);

    this.longMap = new HashMap<>(initialCapacities);
    this.stringMap = new HashMap<>(initialCapacities);
    this.listMap = new HashMap<>(initialCapacities);
    this.setMap = new HashMap<>(initialCapacities);
  }

  @Override
  public void run() {
    while (true) {
      Operation<?, ?, ?> op;
      try {
        op = this.opQueue.take();
      } catch (InterruptedException e) {
        e.printStackTrace();
        return;
      }

      if (op instanceof LongOperation) {
        ((LongOperation<?, ?>)op).execute(this.longMap);
      } else if (op instanceof StringOperation) {
        ((StringOperation<?, ?>)op).execute(this.stringMap);
      }
    }
  }

  public void put(Operation<?, ?, ?> op) throws InterruptedException {
    this.opQueue.put(op);
  }
}
