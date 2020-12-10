package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class ListRemove extends ListOperation<Long, ReDuber.Status> {
  ListRemove(CompletableFuture<ReDuber.Status> result, String key, Long args) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, SortedArray<Long>> db) {
    SortedArray<Long> list = db.get(this.getKey());
    try {
      list.remove(this.getArgs());
    } catch (Exception e) {
      this.getResult().complete(ReDuber.Status.ERROR);
      return;
    }
    this.getResult().complete(ReDuber.Status.OK);
  }
}
