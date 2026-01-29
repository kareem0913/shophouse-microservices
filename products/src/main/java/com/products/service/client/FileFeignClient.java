package com.products.service.client;

import com.products.model.dto.file.ImageResponse;
import com.products.model.dto.file.ImageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "files", fallback =  FileFallback.class)
public interface FileFeignClient {
    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ImageResponse saveImagesApi(ImageRequest images);

    @PostMapping(value = "delete")
    ImageResponse deleteImagesApi(@RequestBody List<String> images);
}
