package com.products.util;

import com.products.error.exception.FileStorageException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.nio.file.Path;

@UtilityClass
@Slf4j
public class Util {
    public static Timestamp currentTimestamp() {
        return Timestamp.from(Instant.now());
    }

    public static String saveFile(MultipartFile file, Path baseUploadDir, String subfolder) {
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
        try {
            Path targetDir = baseUploadDir.resolve(subfolder).toAbsolutePath().normalize();

            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            Path target = targetDir.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            log.info("File {} saved successfully to {}", uniqueFilename, target.toString());
            return subfolder + "/" + uniqueFilename;
        } catch (IOException ex) {
            log.error("Could not store file {}: {}", uniqueFilename, ex.getMessage());
            throw new FileStorageException(
                    "Could not store file " + uniqueFilename + ". Please try again!",
                    "File storage error"
            );
        }
    }

    public static void deleteFile(Path baseUploadDir, String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            return;
        }

        try {
            Path filePath = baseUploadDir.resolve(relativePath).toAbsolutePath().normalize();
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("File {} deleted successfully", filePath.toString());
            } else {
                log.warn("File {} does not exist, nothing to delete", filePath.toString());
            }
        } catch (IOException ex) {
            log.error("Could not delete file {}: {}", relativePath, ex.getMessage());
            throw new FileStorageException(
                    "Could not delete file " + relativePath + ". Please try again!",
                    "File deletion error"
            );
        }
    }

}
