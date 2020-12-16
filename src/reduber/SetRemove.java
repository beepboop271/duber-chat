package reduber;

import java.util.Map;
import java.util.Set;

/**
 * An operation which removes a long from a set.
 *
 * @author Kevin Qiao
 */
class SetRemove extends SetOperation<Long, ReDuber.Status> {
  SetRemove(String key, Long args) {
    super(key, args);
  }

  @Override
  void execute(Map<String, Set<Long>> db) {
    Set<Long> set = db.get(this.getKey());
    if ((set == null) || !(set.remove(this.getArgs()))) {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
    } else {
      this.getResult().complete(ReDuber.Status.OK);
    }
  }
}
