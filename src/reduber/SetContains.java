package reduber;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

class SetContains extends SetOperation<Long, ReDuber.Status> {
  SetContains(CompletableFuture<ReDuber.Status> result, String key, Long args) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, Set<Long>> db) {
    Set<Long> set = db.get(this.getKey());
    if ((set == null) || !(set.contains(this.getArgs()))) {
      this.getResult().complete(ReDuber.Status.FALSE);
    } else {
      this.getResult().complete(ReDuber.Status.TRUE);
    }
  }
}
