package reduber;

import java.util.concurrent.CompletableFuture;

class ReDuberRequest<I, O> {
  private final CompletableFuture<O> result;
  private final ReDuberStore.Operation operation;
  private final String key;
  private final I args;

  ReDuberRequest(
    CompletableFuture<O> result,
    ReDuberStore.Operation operation,
    String key,
    I args
  ) {
    this.result = result;
    this.operation = operation;
    this.key = key;
    this.args = args;
  }

  CompletableFuture<O> getResult() {
    return this.result;
  }

  ReDuberStore.Operation getOperation() {
    return this.operation;
  }

  String getKey() {
    return this.key;
  }

  I getArgs() {
    return this.args;
  }
}
