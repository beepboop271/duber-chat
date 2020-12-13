package reduber;

import java.util.concurrent.CompletableFuture;

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
      +"[id="+id
      +", args="+args
      +", key="+key
      +", result="+result
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
