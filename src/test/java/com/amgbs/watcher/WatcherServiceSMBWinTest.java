package com.amgbs.watcher;

import com.amgbs.watcher.service.WatcherServiceSMBWin;
import jcifs.SmbResource;
import jcifs.smb.SmbFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import java.io.IOException;
public class WatcherServiceSMBWinTest {



    @Test
    public void ifNewFileAddedThenActionAdded() throws IOException, InterruptedException {
        String remoteFolderPath = "smb://192.168.0.107/shared";
        String expected = "File /test/test.txt was added";
        final String[] result = {""};
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                WatcherServiceSMBWin watcherService = new WatcherServiceSMBWin();
               result[0] =  watcherService.oneTimeMonitoring(new SmbFile(remoteFolderPath)).get(0);
            }
        }).start();

        String testDirName = "/test";
        String testFileName = "/test.txt";
        SmbFile smbFile = new SmbFile(remoteFolderPath + testDirName);
        smbFile.mkdir();
        SmbResource res = smbFile.resolve(testFileName);
        res.createNewFile();
//        smbFile = (SmbFile)res;
//        try(SmbFileOutputStream out = new SmbFileOutputStream(smbFile)) {
//            String content = "this is test";
//            out.write(content.getBytes());
//        }
        assert (expected.equals(result[0]));
        }
    }
