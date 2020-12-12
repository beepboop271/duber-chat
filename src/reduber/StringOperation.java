package reduber;

abstract class StringOperation<I, O> extends Operation<String, I, O> {
  StringOperation(String key, I args) {
    super(key, args);
  }
}
