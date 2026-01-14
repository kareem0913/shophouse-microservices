package com.files.controller;

import com.files.dto.GlobalResponse;
import com.files.dto.ImageRequest;
import com.files.service.image.ImageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GlobalResponse save(@Valid @NotNull @ModelAttribute ImageRequest request){
        return imageService.save(request);
    }

    @PostMapping(value = "delete")
    public GlobalResponse delete(@RequestBody List<String> images){
        return imageService.delete(images);
    }
}
