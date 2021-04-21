package com.show.showticketingservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String registerImage(MultipartFile image);

    String saveFile(String fileName, MultipartFile image);

    void deleteFile(String imagePath);

    void checkFileContentType(MultipartFile image);

}
