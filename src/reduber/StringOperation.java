package reduber;

import java.util.concurrent.CompletableFuture;

abstract class StringOperation<I, O> extends Operation<String, I, O> {
  StringOperation(CompletableFuture<O> result, String key, I args) {
    super(result, key, args);
  }
}
