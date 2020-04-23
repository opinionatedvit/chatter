package com.amgbs.watcher.service;

import jcifs.FileNotifyInformation;
import jcifs.SmbWatchHandle;
import jcifs.smb.SmbFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
@Profile("{remote}")
public class WatcherServiceSMBWin implements WatcherService {
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    private SmbFile smbFile;

    @Autowired
    public WatcherServiceSMBWin(SmbFile smbFile) {
        this.smbFile = smbFile;
    }

    @Override
    public void monitorFolder() throws IOException, InterruptedException {
        while (true) {
            CopyOnWriteArrayList<FileNotifyInformation> list = new CopyOnWriteArrayList<>();
            try {
                list.addAll(getActions(smbFile.watch(FileNotifyInformation.FILE_ACTION_ADDED, true)));
                list.addAll(getActions(smbFile.watch(FileNotifyInformation.FILE_ACTION_REMOVED, true)));
                list.addAll(getActions(smbFile.watch(FileNotifyInformation.FILE_ACTION_MODIFIED, true)));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            for (FileNotifyInformation info : list) {
                log.warn(info.getFileName() + " was " + (info.getAction() == 1 ? "added" : info.getAction() == 2 ? "deleted" : "changed"));
            }
            list.clear();
            Thread.sleep(500);
        }
    }

    private List<FileNotifyInformation> getActions(SmbWatchHandle handle) throws ExecutionException, InterruptedException {
        Future<List<FileNotifyInformation>> list = executorService.submit(handle);
        while (true) {
            if (!list.isDone()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
        return list.get();
    }
}
