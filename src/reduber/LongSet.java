package reduber;

import java.util.Map;

/**
 * An operation which sets a long.
 *
 * @author Kevin Qiao
 */
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
