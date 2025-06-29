package org.burgerbude.burgerstd.filewatcher.api;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the configuration required for registering a directory with a FileWatcher.
 * This configuration defines the directory to monitor, whether subdirectories should
 * be included, the event types to watch for, and the listener to handle detected events.
 * <p>
 * Instances of this class are immutable and can be created using the {@link Builder}.
 */
public class RegistrationConfig {

  private final Path directory;
  private final boolean recursive;
  private final FileWatcherListener listener;
  private final Set<WatchEventType> types;

  private RegistrationConfig(
    Path directory,
    boolean recursive,
    FileWatcherListener listener,
    Set<WatchEventType> types
  ) {
    this.directory = directory;
    this.recursive = recursive;
    this.listener = listener;
    this.types = types;
  }

  public static Builder builder() {
    return new Builder();
  }

  public Path getDirectory() {
    return this.directory;
  }

  public boolean isRecursive() {
    return this.recursive;
  }

  public FileWatcherListener getListener() {
    return this.listener;
  }

  public Set<WatchEventType> getTypes() {
    return this.types;
  }

  public static class Builder {

    private Path directory;
    private boolean recursive;
    private FileWatcherListener listener;
    private Set<WatchEventType> types;

    public Builder setDirectory(Path directory) {
      this.directory = directory;
      return this;
    }

    public Builder setRecursive(boolean recursive) {
      this.recursive = recursive;
      return this;
    }

    public Builder setListener(FileWatcherListener listener) {
      this.listener = listener;
      return this;
    }

    public Builder addType(WatchEventType type) {
      if (this.types == null) {
        this.types = new HashSet<>();
      }

      this.types.add(type);
      return this;
    }

    public Builder addTypes(WatchEventType... types) {
      if (this.types == null) {
        this.types = new HashSet<>();
      }

      this.types.addAll(Arrays.asList(types));
      return this;
    }

    /**
     * Builds and returns a {@link RegistrationConfig} instance representing the
     * configuration defined by this builder. The returned configuration includes
     * the directory to monitor, whether subdirectories are included recursively,
     * the event listener to handle events, and the set of event types to be watched.
     * <p>
     * This method enforces validation to ensure all mandatory fields are set:
     * - The directory must not be null.
     * - The listener must not be null.
     * - At least one event type must be specified.
     * <p>
     * If any of these validations fail, an {@link IllegalStateException} is thrown.
     *
     * @return a constructed {@link RegistrationConfig} instance with the specified configuration
     * @throws IllegalStateException if required fields (directory, listener, or types) are not set
     */
    public RegistrationConfig build() {
      if (this.directory == null) {
        throw new IllegalStateException("directory must not be null");
      }

      if (this.listener == null) {
        throw new IllegalStateException("listener must not be null");
      }

      if (this.types == null) {
        throw new IllegalStateException("No types to watch specified");
      }

      return new RegistrationConfig(this.directory, this.recursive, this.listener, this.types);
    }

  }
}
