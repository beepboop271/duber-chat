package reduber;

import java.util.Map;
import java.util.Set;

class SetContains extends SetOperation<Long, ReDuber.Status> {
  SetContains(String key, Long args) {
    super(key, args);
  }

  @Override
  void execute(Map<String, Set<Long>> db) {
    Set<Long> set = db.get(this.getKey());
    if ((set == null) || !(set.contains(this.getArgs()))) {
      this.getResult().complete(ReDuber.Status.FALSE);
    } else {
      this.getResult().complete(ReDuber.Status.TRUE);
    }
  }
}
