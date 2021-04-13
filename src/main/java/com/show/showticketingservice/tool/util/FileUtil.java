package com.show.showticketingservice.tool.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUtil {

    public String renameFile(String originName) {

        UUID uuid = UUID.randomUUID();

        StringBuilder newName = new StringBuilder()
                .append(uuid.toString())
                .append("_")
                .append(originName);

        return newName.toString();
    }

    public void pathCheck(String fileUploadPath) {

        Path dir = Paths.get(fileUploadPath).toAbsolutePath().normalize();

        File path = new File(dir.toString());

        if (!path.exists()) {
            path.mkdirs();
        }
    }

    public boolean isImageFile(String contentType) {
        return contentType.contains("image/jpeg") || contentType.contains("image/png") || contentType.contains("image/gif");
    }
}
