package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class ListAdd extends ListOperation<Long, ReDuber.Status> {
  ListAdd(CompletableFuture<ReDuber.Status> result, String key, Long args) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, SortedArray<Long>> db) {
    SortedArray<Long> list = db.get(this.getKey());
    try {
      if (list == null) {
        list = new SortedArray<Long>();
        db.put(this.getKey(), list);
      }
      list.add(this.getArgs());
    } catch (Exception e) {
      this.getResult().complete(ReDuber.Status.ERROR);
      return;
    }
    this.getResult().complete(ReDuber.Status.OK);
  }
}
