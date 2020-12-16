package reduber;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * An operation which attempts to send a single message to
 * an arbitrary number of users specified by a single key to
 * the String, Set map of the database.
 *
 * @author Kevin Qiao
 */
class PublishMany extends OperationData<Serializable, ReDuber.Status> {
  PublishMany(String key, Serializable args) {
    super(key, args);
  }

  void publishMany(
    Map<String, Set<Long>> db,
    Map<Long, Set<ObjectOutputStream>> loggedInUsers,
    ReDuberPubSub pubSub
  ) {
    Set<Long> subs = db.get(this.getKey());
    if (subs == null) {
      this.getResult().complete(ReDuber.Status.OK);
      return;
    }
    for (Long sub : subs) {
      Set<ObjectOutputStream> streams = loggedInUsers.get(sub);
      if (streams != null) {
        for (ObjectOutputStream stream : streams) {
          pubSub.submit(this.getArgs(), stream);
        }
      }
    }
    this.getResult().complete(ReDuber.Status.OK);
  }
}
