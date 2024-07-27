package com.solux.greenish.Calendar.Service;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Calendar.Dto.WateringIdResponse;
import com.solux.greenish.Calendar.Dto.WateringRequestDto;
import com.solux.greenish.Calendar.Dto.WateringResponseDto;
import com.solux.greenish.Calendar.Repository.WateringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WateringService {
    private final WateringRepository wateringRepository;

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
    public List<WateringResponseDto> getAllWateringByUserId(Long userId) {
        return wateringRepository.findByUserId(userId)
                .stream().map(WateringResponseDto::of).toList();
    }

    // user_id로 완료하지 않은 watering 모두 조회
    @Transactional(readOnly = true)
    public List<WateringResponseDto> getAllWateringByUserIdAndStatus(Long userId) {
        return wateringRepository.findByUserIdAndStatus(userId, Status.PRE)
                .stream().map(WateringResponseDto::of).toList();
    }

    // Date로 watering 조회
    @Transactional(readOnly = true)
    public List<WateringResponseDto> getAllWateringByDate(WateringRequestDto request) {

        return wateringRepository.findByScheduleDateAndUserId(request.getDate(), request.getUserId())
                .stream().map(WateringResponseDto::of).toList();
    }

    // 물주기 완료
    @Transactional
    public WateringIdResponse completeWatering(Long id) {
        Watering watering = getWatering(id);

        watering.updateStatus();

        wateringRepository.save(Watering.builder()
                .plant(watering.getPlant())
                .user(watering.getUser())
                .status(Status.PRE)
                .scheduleDate(watering.getCompleteDate().plusDays(watering.getWateringCycle()))
                .build());

        return WateringIdResponse.of(watering);
    }

    // 물주기 미완료
    @Transactional
    public WateringIdResponse postponeWatering(Long id) {
        Watering watering = getWatering(id);
        watering.postponeWateringDate();
        return WateringIdResponse.of(watering);
    }
}
