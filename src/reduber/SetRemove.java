package reduber;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

class SetRemove extends SetOperation<Long, ReDuber.Status> {
  SetRemove(CompletableFuture<ReDuber.Status> result, String key, Long args) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, HashSet<Long>> db) {
    if (db.get(this.getKey()).remove(this.getArgs())) {
      this.getResult().complete(ReDuber.Status.OK);
    } else {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
    }
  }
}
