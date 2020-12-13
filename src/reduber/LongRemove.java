package reduber;

import java.util.Map;

class LongRemove extends LongOperation<Object, ReDuber.Status> {
  LongRemove(String key) {
    super(key, null);
  }

  @Override
  void execute(Map<String, Long> db) {
    if (db.remove(this.getKey()) == null) {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
    } else {
      this.getResult().complete(ReDuber.Status.OK);
    }
  }
}
