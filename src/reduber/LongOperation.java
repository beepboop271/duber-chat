package reduber;

/**
 * An operation which operates on the String, Long map of
 * the database.
 *
 * @param <I> The type of the operation's argument.
 * @param <O> The type of the operation's result.
 * @author Kevin Qiao
 */
abstract class LongOperation<I, O> extends Operation<Long, I, O> {
  LongOperation(String key, I args) {
    super(key, args);
  }
}
