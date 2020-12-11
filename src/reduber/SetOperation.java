package reduber;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

abstract class SetOperation<I, O> extends Operation<Set<Long>, I, O> {
  SetOperation(CompletableFuture<O> result, String key, I args) {
    super(result, key, args);
  }
}
