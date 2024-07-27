package com.solux.greenish.Photo.Controller;

import com.solux.greenish.Photo.Dto.PresignedUrlDto;
import com.solux.greenish.Photo.Service.PhotoService;
import com.solux.greenish.Response.BasicResponse;
import com.solux.greenish.Response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;
    @PostMapping
    public ResponseEntity<? extends BasicResponse> getPreSingedUrl(
            @RequestBody PresignedUrlDto request) {
        return ResponseEntity.ok(new DataResponse<>(photoService.getPreSignedUrl(request)));
    };
}