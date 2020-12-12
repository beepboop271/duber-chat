package reduber;

import java.util.Map;

abstract class Operation<T, I, O> extends OperationData<I, O> {
  Operation(String key, I args) {
    super(key, args);
  }

  abstract void execute(Map<String, T> db);
}
