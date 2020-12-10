package reduber;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

class SetGet extends SetOperation<Object, Long[]> {
  SetGet(CompletableFuture<Long[]> result, String key) {
    super(result, key, null);
  }

  @Override
  void execute(Map<String, HashSet<Long>> db) {
    this.getResult().complete((Long[])(db.get(this.getKey()).toArray()));
  }
}
