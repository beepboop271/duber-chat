package reduber;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

import logger.Log;

/**
 * Thread-safe interface for the ReDuber data store.
 * <s>Redis: REmote DIctionary Server</s> ReDuber: REmote
 * (not really) DUctionary serBER (Duber isn't the best at
 * spelling).
 *
 * @author Kevin Qiao
 * @version 0.0
 */
public class ReDuber {
  private ReDuberStore store;

  public enum Status {
    OK,
    NO_CHANGE,
    TRUE,
    FALSE,
    ERROR,
  }

  public ReDuber() {
    Log.info("Starting ReDuber", "ReDuber");
    this.store = new ReDuberStore(1024, 10);
    new Thread(this.store).start();
  }

  private <T> CompletableFuture<T> submit(OperationData<?, T> op) {
    this.store.submit(op);
    return op.getResult();
  }

  // long and string ops

  public CompletableFuture<Long> longGet(String key) {
    return this.submit(new LongGet(key));
  }

  public CompletableFuture<String> stringGet(String key) {
    return this.submit(new StringGet(key));
  }

  public CompletableFuture<Status> longRemove(String key) {
    return this.submit(new LongRemove(key));
  }

  public CompletableFuture<Status> stringRemove(String key) {
    return this.submit(new StringRemove(key));
  }

  public CompletableFuture<Status> set(String key, long value) {
    return this.submit(new LongSet(key, value));
  }

  public CompletableFuture<Status> set(String key, String value) {
    return this.submit(new StringSet(key, value));
  }

  public CompletableFuture<Status> setNotExists(String key, long value) {
    return this.submit(new LongSetNotExists(key, value));
  }

  public CompletableFuture<Status> setNotExists(String key, String value) {
    return this.submit(new StringSetNotExists(key, value));
  }

  // list ops

  public CompletableFuture<Status> listAdd(String key, long value) {
    return this.submit(new ListAdd(key, value));
  }

  public CompletableFuture<Status> listRemove(String key, long value) {
    return this.submit(new ListRemove(key, value));
  }

  public CompletableFuture<Long[]> listGet(String key) {
    return this.submit(new ListGet(key));
  }

  public CompletableFuture<Long[]> listGetLast(String key, int amount) {
    return this.submit(new ListGetLast(key, (long)amount));
  }

  public CompletableFuture<Long[]> listGetRange(
    String key,
    long startValue,
    int amount
  ) {
    return this.submit(
      new ListGetRange(key, new Long[] { startValue, (long)amount })
    );
  }

  // set ops

  public CompletableFuture<Status> setAdd(String key, long value) {
    return this.submit(new SetAdd(key, value));
  }

  public CompletableFuture<Status> setRemove(String key, long value) {
    return this.submit(new SetRemove(key, value));
  }

  public CompletableFuture<Status> setContains(String key, long value) {
    return this.submit(new SetContains(key, value));
  }

  public CompletableFuture<Long[]> setGet(String key) {
    return this.submit(new SetGet(key));
  }

  // pubsub

  public CompletableFuture<Status> pubSubLogin(Long value, ObjectOutputStream out) {
    return this.submit(new PubSubLogin(value, out));
  }

  public CompletableFuture<Status> publishDirect(Long recipient, Serializable value) {
    return this.submit(new PublishDirect(recipient, value));
  }

  public CompletableFuture<Status> publishSingle(String key, Serializable value) {
    return this.submit(new PublishSingle(key, value));
  }

  public CompletableFuture<Status> publishMany(String key, Serializable value) {
    return this.submit(new PublishMany(key, value));
  }
}
