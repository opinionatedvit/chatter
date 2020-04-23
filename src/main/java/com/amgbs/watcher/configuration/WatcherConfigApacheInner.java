package com.amgbs.watcher.configuration;

import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("{inner}")
@Configuration
public class WatcherConfigApacheInner {

    @Value("${folder.path}")
    private String folderPath;

    @Bean
    public FileAlterationObserver getFileAlterationObserver() {
        return new FileAlterationObserver(folderPath);
    }
}
