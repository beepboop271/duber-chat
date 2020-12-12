package reduber;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Subscribe extends Operation<Set<Long>, Long, ReDuber.Status> {
  Subscribe(String key, Long args) {
    super(key, args);
  }

  @Override
  void execute(Map<String, Set<Long>> db) {
    Set<Long> subs = db.get(this.getKey());
    if (subs == null) {
      subs = new HashSet<>();
      db.put(this.getKey(), subs);
    }
    subs.add(this.getArgs());
    this.getResult().complete(ReDuber.Status.OK);
  }
}
