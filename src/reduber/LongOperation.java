package reduber;

abstract class LongOperation<I, O> extends Operation<Long, I, O> {
  LongOperation(String key, I args) {
    super(key, args);
  }
}
