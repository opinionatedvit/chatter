package com.amgbs.watcher.service;

import com.amgbs.watcher.utility.ActionType;
import jcifs.CIFSException;
import jcifs.FileNotifyInformation;
import jcifs.SmbWatchHandle;
import jcifs.smb.SmbFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@Profile("{remote}")
public class WatcherServiceSMBWin implements WatcherService {

    @Value("${remote}")
    private String remoteFolderPath;

    @Override
    public void monitorFolder() throws IOException {

        while (true) {
            SmbFile smbFile = new SmbFile("smb://" + remoteFolderPath);
            oneTimeMonitoring(smbFile);
        }
    }

    //divided in parts for testing purpose
    public List<String> oneTimeMonitoring(SmbFile smbFile) throws CIFSException {
        SmbWatchHandle handler = smbFile.watch(2 | 3 | 4 | 5, true);;
        List<String> logs = new LinkedList<>();
        List<FileNotifyInformation> actions = handler.watch();
        actions.forEach(x -> {

                log.warn("File {} was {}", x.getFileName(), ActionType.values()[x.getAction() - 1].getActionName());
                logs.add(String.format("File %s was %s", x.getFileName(), ActionType.values()[x.getAction() - 1].getActionName()));
                });

        handler.close();
        smbFile.close();
        return logs;
    }
}