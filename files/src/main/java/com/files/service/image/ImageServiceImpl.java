package com.files.service.image;

import com.files.dto.GlobalResponse;
import com.files.dto.ImageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.files.util.Util.deleteFile;
import static com.files.util.Util.saveFile;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    private final Path uploadDir;

    public ImageServiceImpl(@Value("${app.properties.upload-path}") String dir) {
        this.uploadDir = Paths.get(dir).toAbsolutePath().normalize();
    }

    @Override
    public GlobalResponse save(ImageRequest request) {
        List<String> imagesUrl = request.getImages()
                .stream()
                .map(image -> saveFile(image, uploadDir, request.getSubFolder()))
                .toList();
        return new GlobalResponse("success", imagesUrl);
    }

    @Override
    public GlobalResponse delete(List<String> imagesName) {
        imagesName.forEach(imageName -> deleteFile(uploadDir, imageName));
        return new GlobalResponse("success", "images is delete successfully");
    }
}
