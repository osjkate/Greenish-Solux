package com.solux.greenish.Environment.Dto;

import com.solux.greenish.User.Dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnvironmentDto {
    private Long id;
    private double latitude;
    private double longitude;
    private UserDto user;
}
