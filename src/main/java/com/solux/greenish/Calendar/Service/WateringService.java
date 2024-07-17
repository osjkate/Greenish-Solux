package com.solux.greenish.Calendar.Service;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Calendar.Dto.WateringDto.*;
import com.solux.greenish.Calendar.Repository.WateringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WateringService {
    private final WateringRepository wateringRepository;

    // plant_id로 watering 모두 조회
    @Transactional(readOnly = true)
    public List<WateringDetailDto> getAllWateringByPlantId(Long plantId) {
        return wateringRepository.findAllByPlantId(plantId)
                .stream().map(WateringDetailDto::of).toList();
    }

    // plant_id로 완료하지 않은 watering 조회
    @Transactional(readOnly = true)
    public WateringDetailDto getScheduledWateringByPlantId(Long plantId) {
        for (Watering w : wateringRepository.findAllByPlantId(plantId)) {
            if (w.getStatus() == Status.PRE) return WateringDetailDto.of(w);
        }
        throw new IllegalArgumentException("물주기 갱신 필요");
    }

    // 주기 업데이트

    // 주기 삭제

    // 주기 생성
}
