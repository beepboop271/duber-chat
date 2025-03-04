package reduber;

import java.util.Map;

/**
 * An operation which gets a long.
 *
 * @author Kevin Qiao
 */
class LongGet extends LongOperation<Object, Long> {
  LongGet(String key) {
    super(key, null);
  }

  @Override
  void execute(Map<String, Long> db) {
    this.getResult().complete(db.get(this.getKey()));
  }
}
