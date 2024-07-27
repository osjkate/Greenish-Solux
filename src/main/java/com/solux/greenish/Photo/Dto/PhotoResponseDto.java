package com.solux.greenish.Photo.Dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PhotoResponseDto {
    private Long photoId;
    private String url;
}
