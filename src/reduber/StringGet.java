package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class StringGet extends StringOperation<Object, String> {
  StringGet(CompletableFuture<String> result, String key) {
    super(result, key, null);
  }

  @Override
  void execute(Map<String, String> db) {
    this.getResult().complete(db.get(this.getKey()));
  }
}
