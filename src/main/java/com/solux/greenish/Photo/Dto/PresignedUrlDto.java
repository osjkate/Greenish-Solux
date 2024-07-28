package com.solux.greenish.Photo.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PresignedUrlDto {
    private Long prefix;
    private String fileName;
}
