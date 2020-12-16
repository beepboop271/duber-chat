package reduber;

import java.util.Set;

/**
 * An operation which operates on the String, Set map of the
 * database.
 *
 * @param <I> The type of the operation's argument.
 * @param <O> The type of the operation's result.
 * @author Kevin Qiao
 */
abstract class SetOperation<I, O> extends Operation<Set<Long>, I, O> {
  SetOperation(String key, I args) {
    super(key, args);
  }
}
