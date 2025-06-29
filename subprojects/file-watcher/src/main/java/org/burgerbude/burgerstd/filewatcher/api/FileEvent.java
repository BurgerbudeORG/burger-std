package org.burgerbude.burgerstd.filewatcher.api;

import java.nio.file.Path;

public record FileEvent(Path file, WatchEventType kind) {

}
