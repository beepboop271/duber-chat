package reduber;

import java.util.Map;

/**
 * An operation which gets a list.
 *
 * @author Kevin Qiao
 */
class ListGet extends ListOperation<Object, Long[]> {
  ListGet(String key) {
    super(key, null);
  }

  @Override
  void execute(Map<String, SortedArray<Long>> db) {
    SortedArray<Long> list = db.get(this.getKey());
    if (list == null) {
      this.getResult().complete(null);
    } else {
      this.getResult().complete(list.toArray());
    }
  }
}
