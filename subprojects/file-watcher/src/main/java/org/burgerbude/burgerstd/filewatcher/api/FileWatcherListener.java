package org.burgerbude.burgerstd.filewatcher.api;

/**
 * This interface defines a listener for file system events. Implementations of this
 * interface can be used to handle file-related events such as creation, modification,
 * and deletion, which are detected by a {@link FileWatcher}.
 * <p>
 * The {@link #onEvent(FileEvent)} method is invoked whenever a registered file system
 * event occurs in the directories being monitored. Use this method to define custom
 * behavior for handling file system events.
 * <p>
 * Implementers of this interface should ensure that the handling logic within the
 * {@link #onEvent(FileEvent)} method is efficient to avoid blocking threads that
 * invoke this listener.
 */
public interface FileWatcherListener {

  /**
   * Handles a file system event detected by a {@link FileWatcher}.
   * This method is invoked whenever an event occurs in one of the directories
   * being monitored. The event provides details about the type of action
   * (e.g., creation, modification, or deletion) and the file to which the
   * action pertains.
   *
   * @param event the {@link FileEvent} representing the file system event,
   *              including the affected file and the type of event.
   */
  void onEvent(FileEvent event);

}
