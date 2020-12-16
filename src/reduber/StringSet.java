package reduber;

import java.util.Map;

/**
 * An operation which sets a string.
 *
 * @author Kevin Qiao
 */
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
