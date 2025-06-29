package org.burgerbude.burgerstd.filewatcher.java;

import org.burgerbude.burgerstd.filewatcher.api.WatchEventType;

import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.Collection;

final class JavaFileWatcherUtil {

  private static final WatchEvent.Kind<?>[] EMPTY_KINDS = new WatchEvent.Kind[0];

  private JavaFileWatcherUtil() {
  }

  public static WatchEvent.Kind<?>[] fromEventTypes(Collection<WatchEventType> types) {
    if (types.isEmpty()) {
      return EMPTY_KINDS;
    }

    WatchEvent.Kind<?>[] kinds = new WatchEvent.Kind[types.size()];
    int index = 0;
    for (WatchEventType type : types) {
      kinds[index++] = fromEventType(type);
    }

    return kinds;
  }

  public static WatchEvent.Kind<?> fromEventType(WatchEventType type) {
    return switch (type) {
      case CREATE -> StandardWatchEventKinds.ENTRY_CREATE;
      case DELETE -> StandardWatchEventKinds.ENTRY_DELETE;
      case MODIFY -> StandardWatchEventKinds.ENTRY_MODIFY;
    };
  }

  public static WatchEventType toEventType(WatchEvent.Kind<?> kind) {
    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
      return WatchEventType.CREATE;
    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
      return WatchEventType.DELETE;
    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
      return WatchEventType.MODIFY;
    } else {
      throw new IllegalArgumentException("Unknown kind: " + kind);
    }
  }
}
