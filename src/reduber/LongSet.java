package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class LongSet extends LongOperation<Long, ReDuber.Status> {
  LongSet(CompletableFuture<ReDuber.Status> result, String key, Long args) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, Long> db) {
    db.put(this.getKey(), this.getArgs());
    this.getResult().complete(ReDuber.Status.OK);
  }
}
