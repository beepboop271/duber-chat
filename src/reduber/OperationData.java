package reduber;

import java.util.concurrent.CompletableFuture;

/**
 * Data which could be used to perform some asynchronous
 * action.
 *
 * @param <I> The type of the operation's argument.
 * @param <O> The type of the operation's result.
 * @author Kevin Qiao
 */
abstract class OperationData<I, O> {
  private final CompletableFuture<O> result;
  private final String key;
  private final I args;
  private final long id;

  OperationData(String key, I args) {
    this.result = new CompletableFuture<>();
    this.key = key;
    this.args = args;
    this.id = ReDuberId.getId();
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
      +"[key="+this.key
      +", args="+this.args
      +", id="+this.id
      +", result="+this.result
      +"]";
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

  long getId() {
    return this.id;
  }
}
