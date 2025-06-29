package org.burgerbude.burgerstd.filewatcher.api;

/**
 * Represents an exception specific to the file watching functionality.
 * This unchecked exception is used to indicate issues that occur during
 * the operation of file system monitoring, such as errors during the
 * creation of a file watcher or other runtime problems.
 * <p>
 * It provides constructors for various use-cases, allowing detailed error
 * messages, underlying causes, and configuration of stack trace and suppression
 * behavior.
 */
public class FileWatcherException extends RuntimeException {

  public FileWatcherException() {
  }

  public FileWatcherException(String message) {
    super(message);
  }

  public FileWatcherException(String message, Throwable cause) {
    super(message, cause);
  }

  public FileWatcherException(Throwable cause) {
    super(cause);
  }

  public FileWatcherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
