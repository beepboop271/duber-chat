package reduber;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Static class to generate unique IDs. Inspired by
 * Twitter's snowflake but at a significantly less
 * distributed scale. The first 48 bits are the unix time in
 * milliseconds at creation time, and the last 16 bits are
 * the value of an atomic counter. Given ID1 > ID2, it is
 * extremely likely that ID1 was generated after ID2.
 *
 * @author Kevin Qiao.
 */
public class ReDuberId {
  private static AtomicInteger count = new AtomicInteger();

  public static long getId() {
    long id = (System.currentTimeMillis() << 16) | count.getAndIncrement();
    count.compareAndSet(1 << 16, 0);
    return id;
  }
}
