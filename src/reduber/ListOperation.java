package reduber;

abstract class ListOperation<I, O> extends
  Operation<SortedArray<Long>, I, O> {

  ListOperation(String key, I args) {
    super(key, args);
  }
}
