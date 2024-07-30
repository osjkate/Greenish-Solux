package com.solux.greenish.Calendar.Service;

import com.solux.greenish.Calendar.Domain.Schedule;
import com.solux.greenish.Calendar.Dto.ScheduleCreateRequestDto;
import com.solux.greenish.Calendar.Dto.ScheduleIdResponseDto;
import com.solux.greenish.Calendar.Dto.ScheduleResponseDto;
import com.solux.greenish.Calendar.Dto.ScheduleUpdateRequestDto;
import com.solux.greenish.Calendar.Repository.ScheduleRepository;
import com.solux.greenish.User.Domain.User;
import com.solux.greenish.User.Repository.UserRepository;
import com.solux.greenish.login.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private User getUserByToken(String token) {
        return userRepository.findByEmail(jwtUtil.getEmail(token.split(" ")[1]))
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 조회할 수 없습니다."));
    }

    // Date로 Schedule 찾기
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getScheduleByDate(String token, LocalDate date) {
        User user = getUserByToken(token);
        return scheduleRepository.findByUserIdAndDate(user.getId(), date)
                .stream().map(ScheduleResponseDto::of).toList();
    }


//     schedule 생성
    @Transactional
    public ScheduleResponseDto createSchedule(String token, ScheduleCreateRequestDto request) {
        User user = getUserByToken(token);
        Schedule schedule = request.toSchedule(user);
        scheduleRepository.save(schedule);
        return ScheduleResponseDto.of(schedule);
    }

    // schedule 모두 조회
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAllSchedule(String token) {
        User user = getUserByToken(token);

        return scheduleRepository.findByUserId(user.getId()).stream()
                .map(ScheduleResponseDto::of)
                .toList();
    }

//    // schedule 수정
//    public ScheduleRepository updateSchedule(ScheduleUpdateRequestDto request) {
//
//    }

}