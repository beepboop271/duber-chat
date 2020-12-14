package reduber;

import java.util.Map;

import logger.Log;

class ListGetLast extends ListOperation<Long, Long[]> {
  ListGetLast(String key, Long args) {
    super(key, args);
  }

  @Override
  void execute(Map<String, SortedArray<Long>> db) {
    SortedArray<Long> list = db.get(this.getKey());
    if (list == null) {
      this.getResult().complete(null);
      return;
    }
    try {
      this.getResult().complete(list.getTailRange(this.getArgs().intValue()));
    } catch (Exception e) {
      Log.warn("Failed to execute", "ReDuberStore", this.getId(), this, e);
      this.getResult().complete(null);
    }
  }
}
