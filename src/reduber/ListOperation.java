package reduber;

import java.util.concurrent.CompletableFuture;

abstract class ListOperation<I, O> extends
  Operation<SortedArray<Long>, I, O> {

  ListOperation(CompletableFuture<O> result, String key, I args) {
    super(result, key, args);
  }
}
