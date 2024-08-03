package com.solux.greenish.Photo.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PresignedUrlDto {
    private String prefix;
    private String fileName;
}
