package com.solux.greenish.Plant.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlantModifyRequestDto {
    private Long plantId;
    private String distbNm;
    private String name;
    private String age;
    private boolean alarm;
    private String fileName;
}