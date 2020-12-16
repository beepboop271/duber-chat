package reduber;

import java.util.Map;
import java.util.Set;

/**
 * An operation which gets a set.
 *
 * @author Kevin Qiao
 */
class SetGet extends SetOperation<Object, Long[]> {
  SetGet(String key) {
    super(key, null);
  }

  @Override
  void execute(Map<String, Set<Long>> db) {
    Set<Long> set = db.get(this.getKey());
    if (set == null) {
      this.getResult().complete(null);
    } else {
      this.getResult().complete(set.toArray(new Long[0]));
    }
  }
}
