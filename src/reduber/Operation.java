package reduber;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

abstract class Operation<T, I, O> {
  private final CompletableFuture<O> result;
  private final String key;
  private final I args;

  Operation(CompletableFuture<O> result, String key, I args) {
    this.result = result;
    this.key = key;
    this.args = args;
  }

  abstract void execute(Map<String, T> db);

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
