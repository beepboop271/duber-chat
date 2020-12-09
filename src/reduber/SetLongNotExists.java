package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class SetLongNotExists extends LongOperation<Long, ReDuber.Status> {
  SetLongNotExists(
    CompletableFuture<ReDuber.Status> result,
    String key,
    Long args
  ) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, Long> db) {
    String key = this.getKey();
    if (db.containsKey(key)) {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
    } else {
      db.put(key, this.getArgs());
      this.getResult().complete(ReDuber.Status.OK);
    }
  }
}
