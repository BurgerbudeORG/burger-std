package org.burgerbude.burgerstd.filewatcher.api;

import org.burgerbude.burgerstd.filewatcher.java.JavaFileWatcher;

import java.io.IOException;
import java.nio.file.FileSystems;

/**
 * The FileWatcher interface provides methods to monitor file system events such as
 * file creation, deletion, and modification. It allows registering directories to
 * be monitored and enables handling events in a controlled manner.
 * <p>
 * By default, {@link FileWatcher#create()} can be used to create an instance of the
 * FileWatcher backed by a WatchService implementation.
 * <p>
 * This interface extends {@link AutoCloseable}, ensuring that resources associated
 * with the file monitoring process can be released properly when the watcher is no longer needed.
 */
public interface FileWatcher extends AutoCloseable {

  /**
   * Creates and returns a new instance of {@code FileWatcher} backed by a WatchService
   * implementation. This instance can be used to monitor file system events such as
   * file creation, deletion, and modification.
   *
   * @return a new {@code FileWatcher} instance
   * @throws FileWatcherException if an error occurs while creating the file watcher
   */
  static FileWatcher create() {
    try {
      return new JavaFileWatcher(FileSystems.getDefault().newWatchService());
    } catch (IOException exception) {
      throw new FileWatcherException("Failed to create file watcher", exception);
    }
  }

  /**
   * Registers a directory for monitoring file system events based on the specified configuration.
   * The registration configuration includes details such as the target directory, event types to monitor,
   * whether subdirectories should be monitored recursively, and the listener to handle the events.
   *
   * @param config the {@link RegistrationConfig} object containing the configuration details for registration.
   * @throws IOException if an I/O error occurs during the registration process.
   */
  void register(RegistrationConfig config) throws IOException;

  /**
   * Polls for file system events within the registered directories and processes them.
   * This method retrieves any pending events from the monitoring service and ensures
   * that the appropriate {@link FileWatcherListener} associated with the registration is invoked
   * to handle each event.
   * <p>
   * The method blocks briefly to wait for events if none are immediately available.
   * It is intended to be called repeatedly, typically in a loop, to continuously
   * process events as they occur.
   * <p>
   * Implementations should handle cases where exceptions may occur during event
   * retrieval or processing and ensure proper continuation of the monitoring process.
   * <p>
   * Note that this method should be used in conjunction with a mechanism to
   * terminate the monitoring process when necessary, such as a flag or thread control,
   * to avoid indefinite blocking.
   */
  void poll();

}
