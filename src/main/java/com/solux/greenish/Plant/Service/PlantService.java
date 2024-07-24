package com.solux.greenish.Plant.Service;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Calendar.Dto.WateringDto.*;
import com.solux.greenish.Calendar.Repository.WateringRepository;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Plant.Dto.PlantDto.*;
import com.solux.greenish.Plant.Repository.PlantRepository;
import com.solux.greenish.Post.Dto.PostDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;

    // id로 식물 조회
    @Transactional(readOnly = true)
    public PlantDetailDto getPlantById(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 식물을 찾을 수 없습니다. "));

        List<PostSimpleDto> posts = plant.getPosts()
                .stream().map(PostSimpleDto::of).toList();

        // TODO : Watering Dto로 변경하기!!
        List<WateringDetailDto> completedWaterings = new ArrayList<>();
        WateringDetailDto scheduledWatering = null;

        for (Watering p : plant.getWaterings()) {
            if (p.getStatus() != Status.PRE) {
                completedWaterings.add(WateringDetailDto.of(p));
            } else scheduledWatering = WateringDetailDto.of(p);
        }
        return PlantDetailDto.of(plant, posts, completedWaterings, scheduledWatering);
    }

    // 식물 전체 조회
    @Transactional(readOnly = true)
    public List<PlantSimpleDto> getAllPlant() {
        return plantRepository.findAll()
                .stream().map(PlantSimpleDto::of).toList();
    }

    // user_id 로 전체 식물 조회
    @Transactional(readOnly = true)
    public List<PlantSimpleDto> getAllPlantByUserId(Long userId) {
        return plantRepository.findByUserId(userId)
                .stream().map(PlantSimpleDto::of).toList();
    }

    // 이름으로 조회
    @Transactional(readOnly = true)
    public List<PlantSimpleDto> getAllPlantByName(String name) {
        return plantRepository.findByName(name)
                .stream().map(PlantSimpleDto::of).toList();
    }

    // 식물 생성

    // 식물 update

    // 식물 삭제

}
