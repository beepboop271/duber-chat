package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class GetLong extends LongOperation<Object, Long> {
  GetLong(CompletableFuture<Long> result, String key) {
    super(result, key, null);
  }

  @Override
  void execute(Map<String, Long> db) {
    this.getResult().complete(db.get(this.getKey()));
  }
}
