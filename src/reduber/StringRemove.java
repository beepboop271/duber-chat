package reduber;

import java.util.Map;

class StringRemove extends StringOperation<Object, ReDuber.Status> {
  StringRemove(String key) {
    super(key, null);
  }

  @Override
  void execute(Map<String, String> db) {
    if (db.remove(this.getKey()) == null) {
      this.getResult().complete(ReDuber.Status.NO_CHANGE);
    } else {
      this.getResult().complete(ReDuber.Status.OK);
    }
  }
}
