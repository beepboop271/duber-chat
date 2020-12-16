package reduber;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An operation which adds a long to a set.
 *
 * @author Kevin Qiao
 */
class SetAdd extends SetOperation<Long, ReDuber.Status> {
  SetAdd(String key, Long args) {
    super(key, args);
  }

  @Override
  void execute(Map<String, Set<Long>> db) {
    Set<Long> set = db.get(this.getKey());
    if (set == null) {
      set = new HashSet<>();
      db.put(this.getKey(), set);
    }
    if (set.add(this.getArgs())) {
      this.getResult().complete(ReDuber.Status.OK);
    } else {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
    }
  }
}
