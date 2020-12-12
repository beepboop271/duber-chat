package reduber;

import java.util.concurrent.CompletableFuture;

abstract class OperationData<I, O> {
  private final CompletableFuture<O> result;
  private final String key;
  private final I args;

  OperationData(String key, I args) {
    this.result = new CompletableFuture<>();
    this.key = key;
    this.args = args;
  }

  CompletableFuture<O> getResult() {
    return this.result;
  }

  String getKey() {
    return this.key;
  }

  I getArgs() {
    return this.args;
  }
}
