package reduber;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

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
    this.store = new ReDuberStore(1024, 10);
    Thread store = new Thread(this.store);
    store.start();
  }

  // long and string ops

  public CompletableFuture<Long> longGet(String key) {
    CompletableFuture<Long> future = new CompletableFuture<>();
    this.store.submit(new LongGet(future, key));
    return future;
  }

  public CompletableFuture<String> stringGet(String key) {
    CompletableFuture<String> future = new CompletableFuture<>();
    this.store.submit(new StringGet(future, key));
    return future;
  }

  public CompletableFuture<Status> set(String key, long value) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new LongSet(future, key, value));
    return future;
  }

  public CompletableFuture<Status> set(String key, String value) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new StringSet(future, key, value));
    return future;
  }

  public CompletableFuture<Status> setNotExists(String key, long value) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new LongSetNotExists(future, key, value));
    return future;
  }

  public CompletableFuture<Status> setNotExists(String key, String value) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new StringSetNotExists(future, key, value));
    return future;
  }

  // list ops

  public CompletableFuture<Status> listAdd(String key, long value) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new ListAdd(future, key, value));
    return future;
  }

  public CompletableFuture<Status> listRemove(String key, long value) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new ListRemove(future, key, value));
    return future;
  }

  public CompletableFuture<Long[]> listGet(String key) {
    CompletableFuture<Long[]> future = new CompletableFuture<>();
    this.store.submit(new ListGet(future, key));
    return future;
  }

  public CompletableFuture<Long[]> listGetRange(
    String key,
    long startValue,
    int amount
  ) {
    CompletableFuture<Long[]> future = new CompletableFuture<>();
    this.store.submit(
      new ListGetRange(future, key, new Long[] { startValue, (long)amount })
    );
    return future;
  }

  // set ops

  public CompletableFuture<Status> setAdd(String key, long value) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new SetAdd(future, key, value));
    return future;
  }

  public CompletableFuture<Status> setRemove(String key, long value) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new SetRemove(future, key, value));
    return future;
  }

  public CompletableFuture<Status> setContains(String key, long value) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new SetContains(future, key, value));
    return future;
  }

  public CompletableFuture<Long[]> setGet(String key) {
    CompletableFuture<Long[]> future = new CompletableFuture<>();
    this.store.submit(new SetGet(future, key));
    return future;
  }

  // pubsub

  public CompletableFuture<Status> subscribe(
    String key,
    ObjectOutputStream value
  ) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new Subscribe(future, key, value));
    return future;
  }

  public CompletableFuture<Status> publish(String key, Serializable value) {
    CompletableFuture<Status> future = new CompletableFuture<>();
    this.store.submit(new Publish(future, key, value));
    return future;
  }
}
