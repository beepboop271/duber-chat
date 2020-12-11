package reduber;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

class SetRemove extends SetOperation<Long, ReDuber.Status> {
  SetRemove(CompletableFuture<ReDuber.Status> result, String key, Long args) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, Set<Long>> db) {
    Set<Long> set = db.get(this.getKey());
    if ((set == null) || !(set.remove(this.getArgs()))) {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
    } else {
      this.getResult().complete(ReDuber.Status.OK);
    }
  }
}
