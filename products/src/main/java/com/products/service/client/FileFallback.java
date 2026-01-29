package com.products.service.client;

import com.products.error.exception.ServiceUnavailableException;
import com.products.model.dto.file.ImageRequest;
import com.products.model.dto.file.ImageResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileFallback implements FileFeignClient{
    @Override
    public ImageResponse saveImagesApi(ImageRequest images) {
        throw new ServiceUnavailableException("file service", "an error happen please tray after some time");
    }

    @Override
    public ImageResponse deleteImagesApi(List<String> images) {
        throw new ServiceUnavailableException("file service", "an error happen please tray after some time");
    }
}
