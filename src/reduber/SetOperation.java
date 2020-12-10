package reduber;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

abstract class SetOperation<I, O> extends Operation<HashSet<Long>, I, O> {
  SetOperation(CompletableFuture<O> result, String key, I args) {
    super(result, key, args);
  }
}
