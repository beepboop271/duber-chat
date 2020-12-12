package reduber;

import java.util.Map;

class StringSet extends StringOperation<String, ReDuber.Status> {
  StringSet(String key, String args) {
    super(key, args);
  }

  @Override
  void execute(Map<String, String> db) {
    db.put(this.getKey(), this.getArgs());
    this.getResult().complete(ReDuber.Status.OK);
  }
}
