package reduber;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import logger.Log;

/**
 * Single-threaded core of the ReDuber database that
 * executes all operations and stores all data. Runs
 * operations received from a queue in an infinite loop.
 *
 * @author Kevin Qiao
 */
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
    Log.info("Starting ReDuberStore", "ReDuberStore");
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
        Log.error("Loop interrupted", "ReDuberStore");
        return;
      }
      OperationData<?, ?> op;
      try {
        op = this.opQueue.take();
      } catch (InterruptedException e) {
        Log.error("Loop interrupted", "ReDuberStore", e);
        return;
      }

      Log.info("Processing operation", "ReDuberStore", op.getId(), op);

      try {
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
        } else if (op instanceof PubSubLogin) {
          ((PubSubLogin)op).login(this.loggedInUsers);
        } else if (op instanceof PublishDirect) {
          ((PublishDirect)op).publishDirect(this.loggedInUsers, this.pubSub);
        } else {
          Log.warn("Unknown operation", "ReDuberStore", op.getId(), op);
        }
        if (op.getResult().isDone()) {
          Log.info("Operation completed", "ReDuberStore", op.getId(), op);
          Log.info("Completion result", "ReDuberStore", op.getId(), op.getResult().get());
        } else {
          Log.warn("Operation finished without returning", "ReDuberStore", op.getId(), op);
        }
      } catch (Exception e) {
        Log.warn("Failed to complete operation", "ReDuberStore", op.getId(), op, e);
      }
    }
  }

  public void submit(OperationData<?, ?> op) {
    this.opQueue.offer(op);
  }
}
