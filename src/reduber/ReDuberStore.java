package reduber;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ReDuberStore implements Runnable, Serializable {
  private static final long serialVersionUID = 0L;

  private transient BlockingQueue<Operation<?, ?, ?>> opQueue;

  private final Map<String, Long> longMap;
  private final Map<String, String> stringMap;
  private final Map<String, SortedArray<Long>> listMap;
  private final Map<String, Set<Long>> setMap;

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
      } else if (op instanceof ListOperation) {
        ((ListOperation<?, ?>)op).execute(this.listMap);
      } else if (op instanceof SetOperation) {
        ((SetOperation<?, ?>)op).execute(this.setMap);
      }
    }
  }

  public void put(Operation<?, ?, ?> op) throws InterruptedException {
    this.opQueue.put(op);
  }
}
