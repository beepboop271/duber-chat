package reduber;

import java.util.Map;

class ListGetRange extends ListOperation<Long[], Long[]> {
  ListGetRange(String key, Long[] args) {
    super(key, args);
  }

  @Override
  void execute(Map<String, SortedArray<Long>> db) {
    Long[] args = this.getArgs();
    SortedArray<Long> list = db.get(this.getKey());
    if ((args.length < 2) || (list == null)) {
      this.getResult().complete(null);
      return;
    }
    try {
      this.getResult().complete(
        list.getRange(args[0], args[1].intValue())
      );
    } catch (Exception e) {
      this.getResult().complete(null);
    }
  }
}
