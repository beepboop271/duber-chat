package logger;

import java.io.PrintStream;
import java.time.Instant;

/**
 * Static class to log information. Provides colouring for
 * various levels of severity, as well as the ability to
 * generate colours from a string hash, for example to make
 * it easier to follow a single user's requests.
 *
 * @author Kevin Qiao
 */
public class Log {
  private static final boolean USE_COLOURS = true;  // 24-bit colour terminals
  private static final String INFO_COLOUR = "\u001B[94m";
  private static final String WARN_COLOUR = "\u001B[93m";
  private static final String ERROR_COLOUR = "\u001B[91m";

  private static PrintStream out = System.out;

  private Log() {
  }

  private static String getTextColour(String text) {
    int code = text.hashCode();
    // large prime number xor to shake up the bits a little so
    // very close ids aren't just off by a few rgb values
    code = code ^ (code*32452843);
    // darkest possible colour is (64, 64, 64)
    int r = (code & 0xff) | 64;
    int g = ((code & 0xff00)>>8) | 64;
    int b = ((code & 0xff0000)>>16) | 64;

    return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
  }

  // lots of overloads follows:

  // log with no id:

  private static void log(String level, String message, String source) {
    Log.out.println(level+" "+Instant.now()+" "+source+" '"+message+"'");
  }

  private static void log(
    String level,
    String message,
    String source,
    Object object
  ) {
    Log.out
      .println(level+" "+Instant.now()+" "+source+" '"+message+"' "+object);
  }

  private static void log(
    String level,
    String message,
    String source,
    Exception error
  ) {
    Log.out.println(level+" "+Instant.now()+" "+source+" '"+message+"'");
    error.printStackTrace(out);
  }

  private static void log(
    String level,
    String message,
    String source,
    Object object,
    Exception error
  ) {
    Log.out.println(level+" "+Instant.now()+" "+source+" '"+message+"' "+object);
    error.printStackTrace(out);
  }

  // log with id:

  private static void log(
    String level,
    String message,
    String source,
    String id
  ) {
    Log.out.println(level+" "+Instant.now()+" "+source+"-"+id+" '"+message+"'");
  }

  private static void log(
    String level,
    String message,
    String source,
    String id,
    Object object
  ) {
    Log.out.println(
      level+" "+Instant.now()+" "+source+"-"+id+" '"+message+"' "+object
    );
  }

  private static void log(
    String level,
    String message,
    String source,
    String id,
    Exception error
  ) {
    Log.out.println(level+" "+Instant.now()+" "+source+"-"+id+" '"+message+"'");
    error.printStackTrace(out);
  }

  private static void log(
    String level,
    String message,
    String source,
    String id,
    Object object,
    Exception error
  ) {
    Log.out.println(
      level+" "+Instant.now()+" "+source+"-"+id+" '"+message+"' "+object
    );
    error.printStackTrace(out);
  }

  // info with no id:

  public static void info(String message, String source) {
    if (Log.USE_COLOURS) {
      log(
        Log.INFO_COLOUR+"INFO",
        message,
        Log.getTextColour(source)+source+Log.INFO_COLOUR
      );
    } else {
      log("INFO", message, source);
    }
  }

  public static void info(String message, String source, Object object) {
    if (Log.USE_COLOURS) {
      log(
        Log.INFO_COLOUR+"INFO",
        message,
        Log.getTextColour(source)+source+Log.INFO_COLOUR,
        object
      );
    } else {
      log("INFO", message, source, object);
    }
  }

  public static void info(String message, String source, Exception error) {
    if (Log.USE_COLOURS) {
      log(
        Log.INFO_COLOUR+"INFO",
        message,
        Log.getTextColour(source)+source+Log.INFO_COLOUR,
        error
      );
    } else {
      log("INFO", message, source, error);
    }
  }

  public static void info(
    String message,
    String source,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.INFO_COLOUR+"INFO",
        message,
        Log.getTextColour(source)+source+Log.INFO_COLOUR,
        object,
        error
      );
    } else {
      log("INFO", message, source, object, error);
    }
  }

  // info with id:

  public static void info(String message, String source, long id) {
    if (Log.USE_COLOURS) {
      log(
        Log.INFO_COLOUR+"INFO",
        message,
        Log.getTextColour(source)+source+Log.INFO_COLOUR,
        Log.getTextColour(""+id)+id+Log.INFO_COLOUR
      );
    } else {
      log("INFO", message, source, ""+id);
    }
  }

  public static void info(
    String message,
    String source,
    long id,
    Object object
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.INFO_COLOUR+"INFO",
        message,
        Log.getTextColour(source)+source+Log.INFO_COLOUR,
        Log.getTextColour(""+id)+id+Log.INFO_COLOUR,
        object
      );
    } else {
      log("INFO", message, source, ""+id, object);
    }
  }

  public static void info(
    String message,
    String source,
    long id,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.INFO_COLOUR+"INFO",
        message,
        Log.getTextColour(source)+source+Log.INFO_COLOUR,
        Log.getTextColour(""+id)+id+Log.INFO_COLOUR,
        error
      );
    } else {
      log("INFO", message, source, ""+id, error);
    }
  }

  public static void info(
    String message,
    String source,
    long id,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.INFO_COLOUR+"INFO",
        message,
        Log.getTextColour(source)+source+Log.INFO_COLOUR,
        Log.getTextColour(""+id)+id+Log.INFO_COLOUR,
        object,
        error
      );
    } else {
      log("INFO", message, source, ""+id, object, error);
    }
  }

  // warn with no id:

  public static void warn(String message, String source) {
    if (Log.USE_COLOURS) {
      log(
        Log.WARN_COLOUR+"WARN",
        message,
        Log.getTextColour(source)+source+Log.WARN_COLOUR
      );
    } else {
      log("WARN", message, source);
    }
  }

  public static void warn(String message, String source, Object object) {
    if (Log.USE_COLOURS) {
      log(
        Log.WARN_COLOUR+"WARN",
        message,
        Log.getTextColour(source)+source+Log.WARN_COLOUR,
        object
      );
    } else {
      log("WARN", message, source, object);
    }
  }

  public static void warn(String message, String source, Exception error) {
    if (Log.USE_COLOURS) {
      log(
        Log.WARN_COLOUR+"WARN",
        message,
        Log.getTextColour(source)+source+Log.WARN_COLOUR,
        error
      );
    } else {
      log("WARN", message, source, error);
    }
  }

  public static void warn(
    String message,
    String source,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.WARN_COLOUR+"WARN",
        message,
        Log.getTextColour(source)+source+Log.WARN_COLOUR,
        object,
        error
      );
    } else {
      log("WARN", message, source, object, error);
    }
  }

  // warn with id:

  public static void warn(String message, String source, long id) {
    if (Log.USE_COLOURS) {
      log(
        Log.WARN_COLOUR+"WARN",
        message,
        Log.getTextColour(source)+source+Log.WARN_COLOUR,
        Log.getTextColour(""+id)+id+Log.WARN_COLOUR
      );
    } else {
      log("WARN", message, source, ""+id);
    }
  }

  public static void warn(
    String message,
    String source,
    long id,
    Object object
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.WARN_COLOUR+"WARN",
        message,
        Log.getTextColour(source)+source+Log.WARN_COLOUR,
        Log.getTextColour(""+id)+id+Log.WARN_COLOUR,
        object
      );
    } else {
      log("WARN", message, source, ""+id, object);
    }
  }

  public static void warn(
    String message,
    String source,
    long id,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.WARN_COLOUR+"WARN",
        message,
        Log.getTextColour(source)+source+Log.WARN_COLOUR,
        Log.getTextColour(""+id)+id+Log.WARN_COLOUR,
        error
      );
    } else {
      log("WARN", message, source, ""+id, error);
    }
  }

  public static void warn(
    String message,
    String source,
    long id,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.WARN_COLOUR+"WARN",
        message,
        Log.getTextColour(source)+source+Log.WARN_COLOUR,
        Log.getTextColour(""+id)+id+Log.WARN_COLOUR,
        object,
        error
      );
    } else {
      log("WARN", message, source, ""+id, object, error);
    }
  }

  // error with no id:

  public static void error(String message, String source) {
    if (Log.USE_COLOURS) {
      log(
        Log.ERROR_COLOUR+"ERROR",
        message,
        Log.getTextColour(source)+source+Log.ERROR_COLOUR
      );
    } else {
      log("ERROR", message, source);
    }
  }

  public static void error(String message, String source, Object object) {
    if (Log.USE_COLOURS) {
      log(
        Log.ERROR_COLOUR+"ERROR",
        message,
        Log.getTextColour(source)+source+Log.ERROR_COLOUR,
        object
      );
    } else {
      log("ERROR", message, source, object);
    }
  }

  public static void error(String message, String source, Exception error) {
    if (Log.USE_COLOURS) {
      log(
        Log.ERROR_COLOUR+"ERROR",
        message,
        Log.getTextColour(source)+source+Log.ERROR_COLOUR,
        error
      );
    } else {
      log("ERROR", message, source, error);
    }
  }

  public static void error(
    String message,
    String source,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.ERROR_COLOUR+"ERROR",
        message,
        Log.getTextColour(source)+source+Log.ERROR_COLOUR,
        object,
        error
      );
    } else {
      log("ERROR", message, source, object, error);
    }
  }

  // error with id:

  public static void error(String message, String source, long id) {
    if (Log.USE_COLOURS) {
      log(
        Log.ERROR_COLOUR+"ERROR",
        message,
        Log.getTextColour(source)+source+Log.ERROR_COLOUR,
        Log.getTextColour(""+id)+id+Log.ERROR_COLOUR
      );
    } else {
      log("ERROR", message, source, ""+id);
    }
  }

  public static void error(
    String message,
    String source,
    long id,
    Object object
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.ERROR_COLOUR+"ERROR",
        message,
        Log.getTextColour(source)+source+Log.ERROR_COLOUR,
        Log.getTextColour(""+id)+id+Log.ERROR_COLOUR,
        object
      );
    } else {
      log("ERROR", message, source, ""+id, object);
    }
  }

  public static void error(
    String message,
    String source,
    long id,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.ERROR_COLOUR+"ERROR",
        message,
        Log.getTextColour(source)+source+Log.ERROR_COLOUR,
        Log.getTextColour(""+id)+id+Log.ERROR_COLOUR,
        error
      );
    } else {
      log("ERROR", message, source, ""+id, error);
    }
  }

  public static void error(
    String message,
    String source,
    long id,
    Object object,
    Exception error
  ) {
    if (Log.USE_COLOURS) {
      log(
        Log.ERROR_COLOUR+"ERROR",
        message,
        Log.getTextColour(source)+source+Log.ERROR_COLOUR,
        Log.getTextColour(""+id)+id+Log.ERROR_COLOUR,
        object,
        error
      );
    } else {
      log("ERROR", message, source, ""+id, object, error);
    }
  }
}
