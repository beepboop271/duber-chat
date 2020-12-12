package reduber;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

class Publish extends OperationData<Serializable, ReDuber.Status> {
  Publish(String key, Serializable args) {
    super(key, args);
  }

  void publish(
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
