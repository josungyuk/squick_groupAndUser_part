package com.handsup.squick.Controller;

import com.handsup.squick.Dto.AttendanceDto.AttendanceDto;
import com.handsup.squick.Dto.GroupDto.Attend.AttendanceCreateDto;
import com.handsup.squick.Service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("attendances")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/")
    ResponseEntity createAttendance(@RequestHeader("AccessToken") String accessToken,
                          @RequestBody AttendanceCreateDto dto){
        boolean response = attendanceService.createAttendance(dto);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/attend")
    ResponseEntity presentAttendance(@RequestHeader("AccessToken") String accessToken,
                              @RequestBody AttendanceCreateDto dto){
        boolean response = attendanceService.bePresent(dto);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/finish")
    long finishAttendance(@RequestHeader("AccessToken") String accessToken,
                          @RequestBody long attendanceId){

    }

    @GetMapping("/timeLeft/{groupId}")
    long getTimeLeft(@RequestHeader("AccessToken") String accessToken,
                          @PathVariable("groupId") long groupId){

    }

    @PostMapping("/check/{attendanceId}")
    long checkAttendance(@RequestHeader("AccessToken") String accessToken,
                          @PathVariable("attendanceId") long attendanceId,
                          @RequestBody AttendanceDto dto){

    }
}
