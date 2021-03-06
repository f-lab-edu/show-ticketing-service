package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.file.InvalidFileTypeException;
import com.show.showticketingservice.tool.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class LocalFileService implements FileService {

    @Value("${spring.servlet.multipart.location}")
    private String fileUploadPath;

    @Override
    public String saveFile(MultipartFile image) {

        String fileName = FileUtil.renameFile(image.getOriginalFilename());

        FileUtil.pathCheck(fileUploadPath);

        StringBuilder filePath = new StringBuilder()
                .append(fileUploadPath)
                .append(File.separator)
                .append(fileName);

        Path path = Paths.get(filePath.toString()).toAbsolutePath().normalize();

        try {
            image.transferTo(new File(path.toString()));

            return path.toString();
        } catch (IOException e) {
            String eMessage = String.format("' %s' 이미지 파일 업로드에 실패하였습니다.", fileName);
            throw new RuntimeException(eMessage, e);
        }
    }

    @Override
    public void deleteFile(String imagePath) {
        new File(imagePath).delete();
    }

    @Override
    public void checkFileContentType(MultipartFile image) {
        String contentType = image.getContentType();

        if (contentType == null || !FileUtil.isImageFile(contentType)) {
            throw new InvalidFileTypeException();
        }

    }
}
