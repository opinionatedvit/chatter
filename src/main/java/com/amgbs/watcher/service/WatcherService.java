package com.amgbs.watcher.service;

import java.io.IOException;

public interface WatcherService {
    void monitorFolder() throws IOException, InterruptedException;
}
