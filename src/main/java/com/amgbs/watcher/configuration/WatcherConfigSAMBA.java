package com.amgbs.watcher.configuration;
import jcifs.smb.NtlmPasswordAuthenticator;
import jcifs.smb.SmbFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.MalformedURLException;

@Profile("{remote}")
@Configuration
public class WatcherConfigSAMBA {
    @Value("${userName}")
    private String remoteUserName;

    @Value("${password}")
    private String remotePassword;

    @Value("${domain}")
    private String domain;

    @Value("${remote}")
    private String remoteFolderPath;

    @Bean
    public NtlmPasswordAuthenticator getNtlmPasswordAuthentificator() {
        return new NtlmPasswordAuthenticator(null, remoteUserName, "");
    }

    @Bean
    public SmbFile getSmbFile() throws MalformedURLException {
        return new SmbFile("smb://" + remoteFolderPath);
    }
}
