package org.burgerbude.burgerstd.filewatcher.java;

import org.burgerbude.burgerstd.filewatcher.api.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JavaFileWatcher implements FileWatcher {

  private final WatchService watcher;
  private final Map<WatchKey, FileWatcherListener> listeners = new HashMap<>();

  public JavaFileWatcher(WatchService watcher) {
    this.watcher = watcher;
  }

  @Override
  public void register(RegistrationConfig config) throws IOException {
    this.register(config.getDirectory(), config.getListener(), config.getTypes());

    if (config.isRecursive()) {
      Files.walkFileTree(config.getDirectory(), new DirectoryVisitor(config, this::register));
    }
  }

  private void register(Path directory, FileWatcherListener listener, Set<WatchEventType> types) throws IOException {
    WatchKey key = directory.register(this.watcher, JavaFileWatcherUtil.fromEventTypes(types));
    this.listeners.put(key, listener);
  }

  @Override
  public void poll() {
    WatchKey key;
    try {
      key = this.watcher.take();
    } catch (ClosedWatchServiceException exception) {
      throw new FileWatcherException("Watch service is closed", exception);
    } catch (InterruptedException exception) {
      throw new FileWatcherException("Interrupted while waiting for key", exception);
    }

    this.dispatchEvents(key);
    key.reset();
  }

  private void dispatchEvents(WatchKey key) {
    if (!(key.watchable() instanceof Path dir)) {
      return;
    }

    FileWatcherListener listener = this.listeners.get(key);
    if (listener == null) {
      return;
    }

    for (var pollEvent : key.pollEvents()) {
      Path file = dir.resolve((Path) pollEvent.context());
      listener.onEvent(new FileEvent(file, JavaFileWatcherUtil.toEventType(pollEvent.kind())));
    }
  }

  @Override
  public void close() throws Exception {
    this.watcher.close();
  }

  static class DirectoryVisitor extends SimpleFileVisitor<Path> {

    private final RegistrationConfig config;
    private final Path startDirectory;
    private final RegistrationConsumer consumer;

    public DirectoryVisitor(RegistrationConfig config, RegistrationConsumer consumer) {
      this.config = config;
      this.startDirectory = config.getDirectory();
      this.consumer = consumer;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
      if (this.startDirectory != dir) {
        this.consumer.accept(dir, this.config.getListener(), this.config.getTypes());
      }

      return FileVisitResult.CONTINUE;
    }
  }

  interface RegistrationConsumer {

    void accept(Path directory, FileWatcherListener listener, Set<WatchEventType> types) throws IOException;

  }
}
