package reduber;

import java.util.Map;

/**
 * An action to be performed on some map in the database.
 *
 * @param <T> The type within the database to operate on.
 * @param <I> The type of the operation's argument.
 * @param <O> The type of the operation's result.
 * @author Kevin Qiao
 */
abstract class Operation<T, I, O> extends OperationData<I, O> {
  Operation(String key, I args) {
    super(key, args);
  }

  abstract void execute(Map<String, T> db);
}
