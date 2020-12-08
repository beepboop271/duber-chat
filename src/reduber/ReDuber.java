package reduber;

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
  private ReDuberPubSub pubsub;

  public enum Status {
    OK,
    NO_CHANGE,
    TRUE,
    FALSE,
    ERROR,
  }

  public ReDuber() {
    this.store = new ReDuberStore(100, 1024);
    Thread store = new Thread(this.store);
    store.start();

    this.pubsub = new ReDuberPubSub(10);
  }
}
