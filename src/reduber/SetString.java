package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

class SetString extends StringOperation<String, ReDuber.Status> {
  SetString(CompletableFuture<ReDuber.Status> result, String key, String args) {
    super(result, key, args);
  }

  @Override
  void execute(Map<String, String> db) {
    db.put(this.getKey(), this.getArgs());
    this.getResult().complete(ReDuber.Status.OK);
  }
}
