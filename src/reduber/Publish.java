package reduber;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

class Publish extends
  OperationData<Set<ObjectOutputStream>, Serializable, ReDuber.Status> {

  Publish(
    CompletableFuture<ReDuber.Status> result,
    String key,
    Serializable args
  ) {
    super(result, key, args);
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
