package com.amgbs.watcher.runners;

import com.amgbs.watcher.service.WatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WatcherServiceRunner implements CommandLineRunner {

    private WatcherService watcherService;

    @Autowired
    public WatcherServiceRunner(WatcherService watcherService) {
        this.watcherService = watcherService;
    }

    @Override
    public void run(String... args) throws IOException, InterruptedException {
        watcherService.monitorFolder();
    }
}
