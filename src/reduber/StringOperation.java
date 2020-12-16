package reduber;

/**
 * An operation which operates on the String, String map of
 * the database.
 *
 * @param <I> The type of the operation's argument.
 * @param <O> The type of the operation's result.
 * @author Kevin Qiao
 */
abstract class StringOperation<I, O> extends Operation<String, I, O> {
  StringOperation(String key, I args) {
    super(key, args);
  }
}
