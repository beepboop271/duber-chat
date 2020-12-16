package reduber;

import java.io.Serializable;
import java.util.Arrays;

public class SortedArray<E extends Comparable<E>> implements Serializable {
  private static final long serialVersionUID = 0L;
  private static final int MIN_CAPACITY = 32;

  private E[] arr;
  private int capacity;
  private int length;

  @SuppressWarnings("unchecked")
  public SortedArray() {
    this.capacity = SortedArray.MIN_CAPACITY;
    this.arr = ((E[])new Comparable[this.capacity]);
    this.length = 0;
  }

  public int add(E element) {
    int index = this.getInsertionIndex(element);

    this.shiftRight(index);
    this.arr[index] = element;
    return this.length;
  }

  // attempts to place equal elements appear later, but not guaranteed
  private int getInsertionIndex(E element) {
    if (this.length == 0) {
      return 0;
    }

    // assume the most likely case is that
    // the element belongs in the first few spots
    for (int i = 1; i <= 5; ++i) {
      if (i > this.length) {
        return 0;
      }
      if (element.compareTo(this.arr[this.length-i]) >= 0) {
        return this.length-i+1;
      }
    }

    // use bounds to prevent null values
    int index = Arrays.binarySearch(this.arr, 0, this.length, element);
    if (index >= 0) {
      // case: equal element already present
      // possible for more equal elements after index
      return index+1;
    }
    return -index-1;
  }

  public int remove(E element) {
    int index = Arrays.binarySearch(this.arr, 0, this.length, element);

    if (index < 0) {
      return -1;
    }

    this.shiftLeft(index);
    return this.length;
  }

  public E[] toArray() {
    return Arrays.copyOf(this.arr, this.length);
  }

  public E[] getTailRange(int amount) {
    if (amount == 0) {
      throw new IllegalArgumentException("nothing requested");
    }
    int from = Math.max(this.length-amount, 0);
    return Arrays.copyOfRange(this.arr, from, this.length);
  }

  public E[] getRange(E start, int amount) {
    if (amount == 0) {
      throw new IllegalArgumentException("nothing requested");
    }
    int index = Arrays.binarySearch(this.arr, 0, this.length, start);
    if (index < 0) {
      throw new IllegalArgumentException("start element not found");
    }
    if (amount > 0) {
      return Arrays.copyOfRange(
        this.arr,
        index,
        Math.min(index+amount, this.length)
      );
    }
    return Arrays.copyOfRange(
      this.arr,
      Math.max(index+amount, 0),
      index
    );
  }

  private void shiftRight(int index) {
    if (this.length == this.capacity) {
      this.capacity <<= 1;
      if (this.capacity < 0) {
        throw new OutOfMemoryError();
      }
      this.arr = Arrays.copyOf(this.arr, this.capacity);
    }

    if (index == this.length) {
      ++this.length;
      return;
    }

    System.arraycopy(this.arr, index, this.arr, index+1, this.length-index);
    ++this.length;
  }

  private void shiftLeft(int index) {
    if (
      (this.length == (this.capacity>>2))
        && (this.capacity > SortedArray.MIN_CAPACITY)
    ) {
      this.capacity >>= 1;
      this.arr = Arrays.copyOf(this.arr, this.capacity);
    }

    if (index == this.length-1) {
      this.arr[this.length-1] = null;
      --this.length;
      return;
    }

    System.arraycopy(this.arr, index+1, this.arr, index, this.length-index-1);
    this.arr[this.length-1] = null;
    --this.length;
  }

  public E peekLast() {
    return this.arr[this.length-1];
  }

  public int size() {
    return this.length;
  }
}
