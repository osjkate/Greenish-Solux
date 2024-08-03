package com.solux.greenish.Plant.Service;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Calendar.Dto.WateringResponseDto;
import com.solux.greenish.Calendar.Repository.WateringRepository;
import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Photo.Dto.PresignedUrlDto;
import com.solux.greenish.Photo.Repository.PhotoRepository;
import com.solux.greenish.Photo.Service.PhotoService;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.Plant.Dto.*;
import com.solux.greenish.Plant.Repository.PlantRepository;
import com.solux.greenish.Post.Domain.Post;
import com.solux.greenish.Post.Dto.PostSimpleResponseDto;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.login.Jwt.JwtUtil;
import com.solux.greenish.search.entity.ApiPlant;
import com.solux.greenish.search.repository.ApiPlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlantService {
    private final PlantRepository plantRepository;
    private final WateringRepository wateringRepository;

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final ApiPlantRepository apiPlantRepository;
    private final PhotoRepository photoRepository;

    private final PhotoService photoService;

    private User getUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 조회할 수 없습니다."));
    }

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

    private Photo getPhoto(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사진을 조회할 수 없습니다. "));
    }

    // id로 식물 조회
    @Transactional(readOnly = true)
    public PlantDetailResponseDto getPlantById(Long id) {
        // plant 찾기
        Plant plant = getPlant(id);

        List<PostSimpleResponseDto> responsePost = new ArrayList<>();
        List<Post> posts = plant.getPosts();
        for (Post post : posts) {
            String imageUrl = null;
            if (post.getPhoto() != null) {
                imageUrl = photoService.getCDNUrl("post/" + plant.getId(), plant.getPhoto().getFileName());
            }
            responsePost.add(PostSimpleResponseDto.of(post, imageUrl));
        }

        String plantPhotoUrl = null;
        if (plant.getPhoto() != null) {
            plantPhotoUrl = photoService.getCDNUrl("plant/" + plant.getId(), plant.getPhoto().getFileName());
        }

        return PlantDetailResponseDto.of(plant, responsePost, plantPhotoUrl);
    }

    // 식물의 물주기 모두 조회
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
        List<Plant> plants = plantRepository.findAll();

        List<PlantSimpleResponseDto> response = new ArrayList<>();
        for (Plant plant : plants) {
            String imageUrl = null;
            if (plant.getPhoto() != null) {
                imageUrl = photoService.getCDNUrl("plant/" + plant.getId(), plant.getPhoto().getFileName());
            }
            response.add(PlantSimpleResponseDto.of(plant, imageUrl));
        }
        return response;
    }

    // user_id 로 전체 식물 조회
    @Transactional(readOnly = true)
    public List<PlantSimpleResponseDto> getAllPlantByUserId(String token) {
        User user = getUserByToken(token);

        List<Plant> plants = plantRepository.findByUserId(user.getId());

        List<PlantSimpleResponseDto> response = new ArrayList<>();
        for (Plant plant : plants) {
            String imageUrl = null;
            if (plant.getPhoto() != null) {
                imageUrl = photoService.getCDNUrl("plant/" + plant.getId(), plant.getPhoto().getFileName());
            }
            response.add(PlantSimpleResponseDto.of(plant, imageUrl));
        }
        return response;
    }

    // 이름으로 조회
    @Transactional(readOnly = true)
    public PlantDetailResponseDto getPlantByName(String name) {
        Plant plant = plantRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 식물을 찾을 수 없습니다. "));
        List<PostSimpleResponseDto> responsePost = new ArrayList<>();
        List<Post> posts = plant.getPosts();
        for (Post post : posts) {
            String imageUrl = null;
            if (post.getPhoto() != null) {
                imageUrl = photoService.getCDNUrl("post/" + plant.getId(), plant.getPhoto().getFileName());
            }
            responsePost.add(PostSimpleResponseDto.of(post, imageUrl));
        }

        String plantPhotoUrl = null;
        if (plant.getPhoto() != null) {
            plantPhotoUrl = photoService.getCDNUrl("plant/" + plant.getId(), plant.getPhoto().getFileName());
        }

        return PlantDetailResponseDto.of(plant, responsePost, plantPhotoUrl);
    }

    // 식물 생성
    // 물주기도 같이 생성됨
    @Transactional
    public PlantResponseDto createPlant(String token, PlantCreateRequestDto request) {

        User user = getUserByToken(token);

        ApiPlant apiPlant = getApiPlant(request.getDistbNm());

        // 해당 유저에게 같은 이름이 있는 plant가 있으면 중복 이름 설정
        if (plantRepository.existsByNameAndUser(request.getName(), user)) {
            throw new IllegalArgumentException("해당 식물 이름이 중복됩니다. ");
        }

        int wateringCycle = apiPlant.getWaterCycle().getCycle();

        Plant plant = request.toEntity(user, wateringCycle, apiPlant);

        plantRepository.save(plant);

        PhotoResponseDto photo = null;

        if (request.getFileName() != null) {
            photo = photoService.createPhoto(PresignedUrlDto.builder()
                    .fileName(request.getFileName())
                    .prefix("plant/" + plant.getId())
                    .build());
            plant.updatePhoto(getPhoto(photo.getPhotoId()));
        }
        createAndSaveWateringSchedules(plant, wateringCycle);

        return PlantResponseDto.builder()
                .plantId(plant.getId())
                .photo(photo)
                .build();
    }


    private void createAndSaveWateringSchedules(Plant plant, int wateringCycle) {
        List<Watering> waterings = new ArrayList<>();
        Watering watering = Watering.builder()
                .plant(plant)
                .user(getUser(plant.getUser().getId()))
                .status(Status.PRE)
                .wateringCycle(wateringCycle)
                .scheduleDate(LocalDate.now().plusDays(wateringCycle))
                .build();

        waterings.add(watering);
        waterings.forEach(wateringRepository::save);
    }

    // 식물 update
    // 물주기도 update 됨
    @Transactional
    public PlantResponseDto updatePlant(PlantModifyRequestDto request) {

        Plant plant = getPlant(request.getPlantId());
        ApiPlant apiPlant = getApiPlant(request.getDistbNm());

        int waterCycle = apiPlant.getWaterCycle().getCycle();

        PhotoResponseDto photo = null;
        String newFileName = request.getFileName();
        String currentFileName = plant.getPhoto().getFileName();
        if (newFileName != null && !newFileName.equals(currentFileName)) {
            photoService.deletePhoto(plant.getPhoto());
            photo = photoService.createPhoto(PresignedUrlDto.builder()
                    .fileName(request.getFileName())
                    .prefix("plant/" + plant.getId())
                    .build());
            plant.updatePhoto(getPhoto(photo.getPhotoId()));
        } else {
            photo = PhotoResponseDto.builder()
                    .photoId(plant.getPhoto().getId())
                    .url(null).build();
        }

        plant.update(request.getName(), request.getAge(), request.isAlarm(),
                waterCycle, apiPlant);


        Watering watering = wateringRepository.findFirstByPlantIdAndStatus(plant.getId(), Status.PRE)
                .orElseThrow(() -> new IllegalArgumentException("물주기 주기가 존재하지 않습니다. "));
        watering.updateWateringDate(plant.getWateringCycle());

        return PlantResponseDto.builder()
                .plantId(plant.getId())
                .photo(photo)
                .build();

    }

    // 식물 하드 삭제 -> 관련 게시물, 물주기 모두 삭제
    @Transactional
    public void deletePlant(Long id) {
        Plant plant = getPlant(id);
        photoService.deletePhoto(plant.getPhoto());
        plantRepository.delete(plant);
    }
}
