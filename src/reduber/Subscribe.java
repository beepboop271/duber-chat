package reduber;

import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;

class Subscribe extends
  Operation<Set<ObjectOutputStream>, ObjectOutputStream, ReDuber.Status> {

  Subscribe(
    CompletableFuture<ReDuber.Status> result,
    String key,
    ObjectOutputStream args
  ) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, Set<ObjectOutputStream>> db) {
    Set<ObjectOutputStream> subs = db.get(this.getKey());
    if (subs == null) {
      // https://www.ibm.com/developerworks/library/j-jtp11225/index.html
      // https://stackoverflow.com/questions/4062919/why-does-exist-weakhashmap-but-absent-weakset
      subs = Collections.newSetFromMap(
        new WeakHashMap<ObjectOutputStream, Boolean>()
      );
      db.put(this.getKey(), subs);
    }
    subs.add(this.getArgs());
    this.getResult().complete(ReDuber.Status.OK);
  }
}
