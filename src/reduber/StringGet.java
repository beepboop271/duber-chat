package reduber;

import java.util.Map;

class StringGet extends StringOperation<Object, String> {
  StringGet(String key) {
    super(key, null);
  }

  @Override
  void execute(Map<String, String> db) {
    this.getResult().complete(db.get(this.getKey()));
  }
}
