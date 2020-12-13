package reduber;

import java.util.Map;

import logger.Log;

class ListAdd extends ListOperation<Long, ReDuber.Status> {
  ListAdd(String key, Long args) {
    super(key, args);
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
      Log.warn("Failed to execute", "ReDuberStore", this.getId(), this, e);
      this.getResult().complete(ReDuber.Status.ERROR);
      return;
    }
    this.getResult().complete(ReDuber.Status.OK);
  }
}
