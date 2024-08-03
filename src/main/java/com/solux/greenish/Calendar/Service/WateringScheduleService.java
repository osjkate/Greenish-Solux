package com.solux.greenish.Calendar.Service;

import com.solux.greenish.Calendar.Domain.Status;
import com.solux.greenish.Calendar.Domain.Watering;
import com.solux.greenish.Calendar.Repository.WateringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WateringScheduleService {
    private final WateringRepository wateringRepository;
    private final WateringService wateringService;

    public void WateringPostpone() {
        List<Watering> waterings = wateringRepository.findAll();
        for (Watering w : waterings) {
            if (w.getStatus() == Status.PRE) {
                wateringService.postponeWatering(w.getId());
            }
        }
    }
}
