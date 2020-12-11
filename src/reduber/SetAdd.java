package reduber;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

class SetAdd extends SetOperation<Long, ReDuber.Status> {
  SetAdd(CompletableFuture<ReDuber.Status> result, String key, Long args) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, Set<Long>> db) {
    Set<Long> set = db.get(this.getKey());
    if (set == null) {
      set = new HashSet<>();
      db.put(this.getKey(), set);
    }
    if (set.add(this.getArgs())) {
      this.getResult().complete(ReDuber.Status.OK);
    } else {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
    }
  }
}
