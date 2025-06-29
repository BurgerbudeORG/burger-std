package org.burgerbude.burgerstd.filewatcher.api;

/**
 * Defines the types of events that can be monitored in a file system.
 * These event types are used to represent actions performed on files
 * or directories, such as creation, deletion, or modification.
 * <p>
 * This enumeration is intended to be used in conjunction with file
 * system watchers, such as {@code FileWatcher}, to specify which types
 * of events should be observed and handled.
 * <p>
 * The available event types are:
 * - CREATE: Represents the creation of a file or directory.
 * - DELETE: Represents the deletion of a file or directory.
 * - MODIFY: Represents any modification to a file or directory, such as
 * changes to its contents or attributes.
 */
public enum WatchEventType {
  CREATE,
  DELETE,
  MODIFY
}
