package reduber;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

class SetContains extends SetOperation<Long, ReDuber.Status> {
  SetContains(CompletableFuture<ReDuber.Status> result, String key, Long args) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, HashSet<Long>> db) {
    if (db.get(this.getKey()).contains(this.getArgs())) {
      this.getResult().complete(ReDuber.Status.TRUE);
    } else {
      this.getResult().complete(ReDuber.Status.FALSE);
    }
  }
}
