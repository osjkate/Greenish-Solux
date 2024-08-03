package com.solux.greenish.Photo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponseDto {
    private Long photoId;
    private String url;
}
