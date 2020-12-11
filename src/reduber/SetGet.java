package reduber;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

class SetGet extends SetOperation<Object, Long[]> {
  SetGet(CompletableFuture<Long[]> result, String key) {
    super(result, key, null);
  }

  @Override
  void execute(Map<String, Set<Long>> db) {
    Set<Long> set = db.get(this.getKey());
    if (set == null) {
      this.getResult().complete(null);
    } else {
      this.getResult().complete((Long[])(set.toArray()));
    }
  }
}
