package reduber;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

class Publish extends OperationData<Serializable, ReDuber.Status> {
  Publish(
    String key,
    Serializable args
  ) {
    super(key, args);
  }

  void execute(Map<String, Set<ObjectOutputStream>> db, ReDuberPubSub pubSub) {
    Set<ObjectOutputStream> subs = db.get(this.getKey());
    if (subs == null) {
      this.getResult().complete(ReDuber.Status.OK);
      return;
    }
    for (ObjectOutputStream out : subs) {
      pubSub.submit(this.getArgs(), out);
    }
    this.getResult().complete(ReDuber.Status.OK);
  }
}
