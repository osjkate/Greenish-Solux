package com.solux.greenish.Plant.Dto;

import com.solux.greenish.Plant.Domain.Plant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IdResponse{
    private Long id;
    public static IdResponse of(Plant plant) {
        return new IdResponse(plant.getId());
    }
}