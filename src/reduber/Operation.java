package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

abstract class Operation<T, I, O> extends OperationData<I, O> {
  Operation(CompletableFuture<O> result, String key, I args) {
    super(result, key, args);
  }

  abstract void execute(Map<String, T> db);
}
