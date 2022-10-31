package com.example.demo.common.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class FileUtil {

    public String generateFileDownloadUri(String fileId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/download/")
                .path(fileId)
                .toUriString();
    }
}
