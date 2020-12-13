package reduber;

import java.util.Map;

import logger.Log;

class ListRemove extends ListOperation<Long, ReDuber.Status> {
  ListRemove(String key, Long args) {
    super(key, args);
  }

  @Override
  void execute(Map<String, SortedArray<Long>> db) {
    SortedArray<Long> list = db.get(this.getKey());
    if (list == null) {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
      return;
    }
    try {
      list.remove(this.getArgs());
    } catch (Exception e) {
      Log.warn("Failed to execute", "ReDuberStore", this.getId(), this, e);
      this.getResult().complete(ReDuber.Status.ERROR);
      return;
    }
    this.getResult().complete(ReDuber.Status.OK);
  }
}
