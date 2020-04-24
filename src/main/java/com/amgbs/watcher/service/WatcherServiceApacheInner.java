package com.amgbs.watcher.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;

@Profile("{inner}")
@Slf4j
@Service
public class WatcherServiceApacheInner implements WatcherService {

    private FileAlterationObserver fileAlterationObserver;

    @Autowired
    public WatcherServiceApacheInner(FileAlterationObserver fileAlterationObserver) {
        this.fileAlterationObserver = fileAlterationObserver;
    }

    @Override
    public void monitorFolder() {

        fileAlterationObserver.addListener(new FileAlterationListenerAdaptor() {
            @Override
            public void onDirectoryCreate(File directory) {
                super.onDirectoryCreate(directory);
                log.warn("Directory " + directory.getAbsolutePath() + " created");
            }

            @Override
            public void onDirectoryChange(File directory) {
                super.onDirectoryChange(directory);
                log.warn("Directory " + directory.getAbsolutePath() + " changed");
            }

            @Override
            public void onDirectoryDelete(File directory) {
                super.onDirectoryDelete(directory);
                log.warn("Directory " + directory.getAbsolutePath() + " deleted");
            }

            @Override
            public void onFileCreate(File file) {
                super.onFileCreate(file);
                log.warn("File " + file.getAbsolutePath() + " created");
            }

            @Override
            public void onFileChange(File file) {
                super.onFileChange(file);
                log.warn("File " + file.getName() + " changed");
            }

            @Override
            public void onFileDelete(File file) {
                super.onFileDelete(file);
                log.warn("File " + file.getName() + " deleted");
            }
        });
        FileAlterationMonitor monitor = new FileAlterationMonitor(500, fileAlterationObserver);
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}