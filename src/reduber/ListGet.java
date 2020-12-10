package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class ListGet extends ListOperation<Object, Long[]> {
  ListGet(CompletableFuture<Long[]> result, String key) {
    super(result, key, null);
  }

  @Override
  void execute(Map<String, SortedArray<Long>> db) {
    this.getResult().complete(db.get(this.getKey()).toArray());
  }
}
