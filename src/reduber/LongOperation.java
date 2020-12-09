package reduber;

import java.util.concurrent.CompletableFuture;

abstract class LongOperation<I, O> extends Operation<Long, I, O> {
  LongOperation(CompletableFuture<O> result, String key, I args) {
    super(result, key, args);
  }
}
