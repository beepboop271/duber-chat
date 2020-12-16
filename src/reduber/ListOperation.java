package reduber;

/**
 * An operation which operates on the String, SortedArray
 * map of the database.
 *
 * @param <I> The type of the operation's argument.
 * @param <O> The type of the operation's result.
 * @author Kevin Qiao
 */
abstract class ListOperation<I, O> extends
  Operation<SortedArray<Long>, I, O> {

  ListOperation(String key, I args) {
    super(key, args);
  }
}
