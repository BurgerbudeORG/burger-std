package org.burgerbude.burgerstd.filewatcher.api;

public class FileWatcherThread extends Thread {

  private final FileWatcher watcher;
  private boolean running;

  public FileWatcherThread(FileWatcher watcher) {
    this.watcher = watcher;
  }

  @Override
  public void start() {
    this.running = true;
    super.start();
  }

  @Override
  public void run() {
    super.run();
    while (this.running) {
      this.watcher.poll();
    }
  }

  @Override
  public void interrupt() {
    super.interrupt();
    this.running = false;
  }
}
