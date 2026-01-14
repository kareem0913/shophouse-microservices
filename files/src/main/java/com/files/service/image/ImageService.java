package com.files.service.image;

import com.files.dto.GlobalResponse;
import com.files.dto.ImageRequest;

import java.util.List;

public interface ImageService {
    GlobalResponse save(ImageRequest request);
    GlobalResponse delete(List<String> imagesName);
}
