package reduber;

import java.util.Map;

class LongSet extends LongOperation<Long, ReDuber.Status> {
  LongSet(String key, Long args) {
    super(key, args);
  }

  @Override
  void execute(Map<String, Long> db) {
    db.put(this.getKey(), this.getArgs());
    this.getResult().complete(ReDuber.Status.OK);
  }
}
