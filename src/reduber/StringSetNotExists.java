package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class StringSetNotExists extends StringOperation<String, ReDuber.Status> {
  StringSetNotExists(
    CompletableFuture<ReDuber.Status> result,
    String key,
    String args
  ) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, String> db) {
    String key = this.getKey();
    if (db.containsKey(key)) {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
    } else {
      db.put(key, this.getArgs());
      this.getResult().complete(ReDuber.Status.OK);
    }
  }
}
