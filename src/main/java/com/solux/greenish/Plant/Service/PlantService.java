package com.solux.greenish.Plant.Service;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Calendar.Dto.WateringResponseDto;
import com.solux.greenish.Calendar.Repository.WateringRepository;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Plant.Dto.*;
import com.solux.greenish.Plant.Repository.PlantRepository;
import com.solux.greenish.Post.Dto.PostDto.PostSimpleDto;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.search.entity.ApiPlant;
import com.solux.greenish.search.repository.ApiPlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;
    private final WateringRepository wateringRepository;
    private final UserRepository userRepository;
    private final ApiPlantRepository apiPlantRepository;

    private Plant getPlant(Long plantId) {
        return plantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("식물을 찾을 수 없습니다. "));
    }

    private ApiPlant getApiPlant(String disbNm) {
        return apiPlantRepository.findByDistbNm(disbNm);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 조회할 수 없습니다."));
    }

    // id로 식물 조회
    @Transactional(readOnly = true)
    public PlantDetailResponseDto getPlantById(Long id) {
        // plant 찾기
        Plant plant = getPlant(id);

        // post들 조회
        List<PostSimpleDto> posts = plant.getPosts()
                .stream().map(PostSimpleDto::of).toList();

        return PlantDetailResponseDto.of(plant, posts);
    }

    // 식물의 물주기 조회
    @Transactional(readOnly = true)
    public PlantWateringResponseDto getWatering(Long plantId) {
        // 워터링 조회하면서 완료한 것과 완료하지 않은 것으로 나눠서 조회
        List<WateringResponseDto> completedWaterings = wateringRepository.findByPlantIdAndStatus(plantId, Status.COMPLETED)
                .stream().map(WateringResponseDto::of).toList();
        WateringResponseDto scheduledWatering = WateringResponseDto.of(wateringRepository.findFirstByPlantIdAndStatus(plantId, Status.PRE).get());
        return PlantWateringResponseDto.of(completedWaterings, scheduledWatering);
    }

    // 식물 전체 조회
    @Transactional(readOnly = true)
    public List<PlantSimpleResponseDto> getAllPlant() {
        return plantRepository.findAll()
                .stream().map(PlantSimpleResponseDto::of).toList();
    }

    // user_id 로 전체 식물 조회
    @Transactional(readOnly = true)
    public List<PlantSimpleResponseDto> getAllPlantByUserId(Long userId) {
        return plantRepository.findByUserId(userId)
                .stream().map(PlantSimpleResponseDto::of).toList();
    }

    // 이름으로 조회
    @Transactional(readOnly = true)
    public PlantSimpleResponseDto getPlantByName(String name) {
        return plantRepository.findByName(name).map(PlantSimpleResponseDto::of)
                .orElseThrow(() -> new IllegalArgumentException("해당 식물을 조회할 수 없습니다. "));
    }

    // 식물 생성
    // 물주기도 같이 생성됨
    @Transactional
    public IdResponse createPlant(PlantRegistrationRequestDto request) {
        User user = getUser(request.getUserId());
        ApiPlant apiPlant = getApiPlant(request.getDistbNm());

        if (plantRepository.existsByName(request.getName())) {
            throw new RuntimeException("해당 식물 이름이 중복됩니다. ");
        }

        int wateringCycle = getWateringCycle(user, apiPlant);

        Plant plant = request.toEntity(user, wateringCycle, apiPlant);

        plantRepository.save(plant);


        List<Watering> waterings = new ArrayList<>();
        Watering watering = Watering.builder()
                .plant(getPlant(plant.getId()))
                .status(Status.PRE)
                .scheduleDate(LocalDate.now().plusDays(wateringCycle))
                .build();

        waterings.add(watering);
        waterings.forEach(wateringRepository::save);

        return IdResponse.of(plant);
    }

    // TODO : 물주기 도출로직
    public int getWateringCycle(User user, ApiPlant apiPlant) {
        return 3;
    }

    // 식물 update
    // 물주기도 update 됨
    @Transactional
    public IdResponse updatePlant(PlantModifyRequestDto request) {

        Plant plant = getPlant(request.getPlantId());
        ApiPlant apiPlant = getApiPlant(request.getDistbNm());

        int waterCycle = getWateringCycle(getUser(plant.getUser().getId()), apiPlant);

        plant.update(request.getName(), request.getAge(), request.isAlarm(),
                waterCycle, apiPlant, request.getPhotoPath());

        Watering watering = wateringRepository.findFirstByPlantIdAndStatus(plant.getId(), Status.PRE)
                .orElseThrow(() -> new IllegalArgumentException("물주기 주기가 존재하지 않습니다. "));
        watering.updateWateringDate(plant.getWateringCycle());

        return IdResponse.of(plant);
    }

    // 식물 하드 삭제 -> 관련 게시물, 물주기 모두 삭제
    @Transactional
    public void deletePlant(Long id) {
        plantRepository.delete(getPlant(id));
    }

    // TODO : soft delete 나중에 구현
    @Transactional
    public void softDeletePlant() {

    }
}
