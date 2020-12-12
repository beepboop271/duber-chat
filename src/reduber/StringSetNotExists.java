package reduber;

import java.util.Map;

class StringSetNotExists extends StringOperation<String, ReDuber.Status> {
  StringSetNotExists(
    String key,
    String args
  ) {
    super(key, args);
  }

  @Override
  void execute(Map<String, String> db) {
    String key = this.getKey();
    if (db.containsKey(key)) {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
    } else {
      db.put(key, this.getArgs());
      this.getResult().complete(ReDuber.Status.OK);
    }
  }
}
