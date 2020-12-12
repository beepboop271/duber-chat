package reduber;

import java.util.Set;

abstract class SetOperation<I, O> extends Operation<Set<Long>, I, O> {
  SetOperation(String key, I args) {
    super(key, args);
  }
}
