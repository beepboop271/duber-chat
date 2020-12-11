package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class ListGet extends ListOperation<Object, Long[]> {
  ListGet(CompletableFuture<Long[]> result, String key) {
    super(result, key, null);
  }

  @Override
  void execute(Map<String, SortedArray<Long>> db) {
    SortedArray<Long> list = db.get(this.getKey());
    if (list == null) {
      this.getResult().complete(null);
    } else {
      this.getResult().complete(list.toArray());
    }
  }
}
