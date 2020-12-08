package reduber;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ReDuberStore implements Runnable, Serializable {
  private static final long serialVersionUID = 0L;

  private transient BlockingQueue<ReDuberRequest<?, ?>> opQueue;

  private final HashMap<String, Long> longMap;
  private final HashMap<String, String> stringMap;
  private final HashMap<String, ArrayDeque<Long>> listMap;
  private final HashMap<String, HashSet<Long>> setMap;

  enum Operation {
    GET_LONG,
    SET_LONG,
    SET_LONG_NOT_EXISTS,
    GET_STRING,
    SET_STRING,
    SET_STRING_NOT_EXISTS,
    LIST_APPEND,
    LIST_REMOVE,
    LIST_GET,
    LIST_GET_PARTIAL,
    SET_ADD,
    SET_REMOVE,
    SET_CONTAINS,
    SET_GET,
  }

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
      ReDuberRequest<?, ?> op;
      try {
        op = this.opQueue.take();
      } catch (InterruptedException e) {
        e.printStackTrace();
        return;
      }

      switch (op.getOperation()) {

      }
    }
  }

  public void put(ReDuberRequest<?, ?> op) throws InterruptedException {
    this.opQueue.put(op);
  }
}
