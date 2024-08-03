package com.solux.greenish.Calendar.Service;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Calendar.Dto.WateringIdResponseDto;
import com.solux.greenish.Calendar.Dto.WateringResponseDto;
import com.solux.greenish.Calendar.Dto.WateringResponseMainDto;
import com.solux.greenish.Calendar.Repository.WateringRepository;
import com.solux.greenish.Photo.Repository.PhotoRepository;
import com.solux.greenish.Photo.Service.PhotoService;
import com.solux.greenish.Plant.Domain.Plant;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.login.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WateringService {
    private final WateringRepository wateringRepository;
    private final UserRepository userRepository;
    private final PhotoService photoService;
    private final JwtUtil jwtUtil;

    private User getUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 조회할 수 없습니다."));
    }

    private Watering getWatering(Long wateringId) {
        return wateringRepository.findById(wateringId)
                .orElseThrow(() -> new IllegalArgumentException("물주기를 조회할 수 없습니다. "));
    }

    // plant_id로 watering 모두 조회
    @Transactional(readOnly = true)
    public List<WateringResponseDto> getAllWateringByPlantId(Long plantId) {
        return wateringRepository.findByPlantId(plantId)
                .stream().map(WateringResponseDto::of).toList();
    }

    // plant_id로 완료하지 않은 watering 조회
    @Transactional(readOnly = true)
    public WateringResponseDto getScheduledWateringByPlantId(Long plantId) {
        return wateringRepository.findFirstByPlantIdAndStatus(plantId, Status.PRE)
                .map(WateringResponseDto::of)
                .orElseThrow(() -> new IllegalArgumentException("해당 식물을 찾을 수 없습니다. "));
    }

    // user_id로 watering 모두 조회
    @Transactional(readOnly = true)
    public List<WateringResponseDto> getAllWateringByUserId(String token) {
        User user = getUserByToken(token);
        return wateringRepository.findByUserId(user.getId())
                .stream().map(WateringResponseDto::of).toList();
    }

    // user_id로 완료하지 않은 watering 모두 조회
    @Transactional(readOnly = true)
    public List<WateringResponseDto> getAllWateringByUserIdAndStatus(String token) {
        User user = getUserByToken(token);
        return wateringRepository.findByUserIdAndStatus(user.getId(), Status.PRE)
                .stream().map(WateringResponseDto::of).toList();
    }

    // Date로 watering 조회
    @Transactional(readOnly = true)
    public List<WateringResponseDto> getAllWateringByDate(String token, LocalDate date) {
        User user = getUserByToken(token);

        return wateringRepository.findByScheduleDateAndUserId(date, user.getId())
                .stream().map(WateringResponseDto::of).toList();
    }

    // 물주기 완료
    @Transactional
    public WateringIdResponseDto completeWatering(Long id) {
        Watering watering = getWatering(id);

        watering.updateStatus();

        wateringRepository.save(Watering.builder()
                .plant(watering.getPlant())
                .user(watering.getUser())
                .status(Status.PRE)
                .scheduleDate(watering.getCompleteDate().plusDays(watering.getWateringCycle()))
                .build());

        return WateringIdResponseDto.of(watering);
    }

    // 물주기 미완료
    @Transactional
    public WateringIdResponseDto postponeWatering(Long id) {
        Watering watering = getWatering(id);
        watering.postponeWateringDate();
        return WateringIdResponseDto.of(watering);
    }

    // 메인화면 물주기 조회
    @Transactional(readOnly = true)
    public List<WateringResponseMainDto> getAllWateringSinceToday(String token) {
        User user = getUserByToken(token);
        List<WateringResponseMainDto> response = new ArrayList<>();
        List<Watering> waterings = wateringRepository.findByUserIdAndScheduleDateAfter(user.getId(), LocalDate.now());
        for (Watering w : waterings) {
            Plant plant = w.getPlant();
            String imageUrl = null;
            if (w.getPlant().getPhoto() != null) {
                imageUrl = photoService.getCDNUrl("plant/" + plant.getId(), plant.getPhoto().getFileName());
            }
            response.add(WateringResponseMainDto.toDto(w, imageUrl));
        }
        return response;
    }
}
