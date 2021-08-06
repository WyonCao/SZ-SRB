package com.wyon.srb.oss.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String upload(String module, MultipartFile file);
}
