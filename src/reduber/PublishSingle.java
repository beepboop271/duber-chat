package reduber;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * An operation which attempts to send a single message to a
 * user specified by a single key to the String, Long map of
 * the database.
 *
 * @author Kevin Qiao
 */
class PublishSingle extends OperationData<Serializable, ReDuber.Status> {
  PublishSingle(String key, Serializable args) {
    super(key, args);
  }

  void publishSingle(
    Map<String, Long> db,
    Map<Long, Set<ObjectOutputStream>> loggedInUsers,
    ReDuberPubSub pubSub
  ) {
    Long sub = db.get(this.getKey());
    if (sub == null) {
      this.getResult().complete(ReDuber.Status.OK);
      return;
    }
    Set<ObjectOutputStream> streams = loggedInUsers.get(sub);
    if (streams != null) {
      for (ObjectOutputStream stream : streams) {
        pubSub.submit(this.getArgs(), stream);
      }
    }
    this.getResult().complete(ReDuber.Status.OK);
  }
}
