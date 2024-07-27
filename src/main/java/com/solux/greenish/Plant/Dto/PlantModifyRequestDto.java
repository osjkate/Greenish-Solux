package com.solux.greenish.Plant.Dto;

import lombok.Getter;

@Getter
public class PlantModifyRequestDto {
    private Long plantId;
    private String distbNm;
    private String name;
    private String age;
    private boolean alarm;
    private String photoPath;
}