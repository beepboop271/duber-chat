package reduber;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ReDuberStore implements Runnable, Serializable {
  private static final long serialVersionUID = 0L;

  private final transient BlockingQueue<OperationData<?, ?>> opQueue;
  private final transient ReDuberPubSub pubSub;

  private final Map<String, Long> longMap;
  private final Map<String, String> stringMap;
  private final Map<String, SortedArray<Long>> listMap;
  private final Map<String, Set<Long>> setMap;

  private final transient Map<Long, Set<ObjectOutputStream>> loggedInUsers;

  ReDuberStore(int initialCapacities, int pubSubThreads) {
    this.opQueue = new LinkedBlockingQueue<>();
    this.pubSub = new ReDuberPubSub(pubSubThreads);

    this.longMap = new HashMap<>(initialCapacities);
    this.stringMap = new HashMap<>(initialCapacities);
    this.listMap = new HashMap<>(initialCapacities);
    this.setMap = new HashMap<>(initialCapacities);

    this.loggedInUsers = new HashMap<>(initialCapacities);
  }

  @Override
  public void run() {
    while (true) {
      if (Thread.interrupted()) {
        return;
      }
      OperationData<?, ?> op;
      try {
        op = this.opQueue.take();
      } catch (InterruptedException e) {
        e.printStackTrace();
        return;
      }

      if (op instanceof PublishMany) {
        ((PublishMany)op).publishMany(
          this.setMap,
          this.loggedInUsers,
          this.pubSub
        );
      } else if (op instanceof PublishSingle) {
        ((PublishSingle)op).publishSingle(
          this.longMap,
          this.loggedInUsers,
          this.pubSub
        );
      } else if (op instanceof LongOperation) {
        ((LongOperation<?, ?>)op).execute(this.longMap);
      } else if (op instanceof StringOperation) {
        ((StringOperation<?, ?>)op).execute(this.stringMap);
      } else if (op instanceof ListOperation) {
        ((ListOperation<?, ?>)op).execute(this.listMap);
      } else if (op instanceof SetOperation) {
        ((SetOperation<?, ?>)op).execute(this.setMap);
      } else if (op instanceof Register) {
        ((Register)op).register(this.loggedInUsers);
      }
    }
  }

  public void submit(OperationData<?, ?> op) {
    this.opQueue.offer(op);
  }
}
