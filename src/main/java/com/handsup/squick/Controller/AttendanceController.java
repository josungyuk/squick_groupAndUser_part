package com.handsup.squick.Controller;

import com.handsup.squick.Dto.AttendanceDto.AttendanceDto;
import com.handsup.squick.Dto.GroupDto.Attend.AttendanceCreateDto;
import com.handsup.squick.Service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("attendances")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/")
    ResponseEntity createAttendance(@RequestHeader("AccessToken") String accessToken,
                          @RequestBody AttendanceCreateDto dto){
        long response = attendanceService.createAttendance(dto);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PostMapping("/attend")
    ResponseEntity presentAttendance(@RequestHeader("AccessToken") String accessToken,
                              @RequestBody AttendanceCreateDto dto){
        long response = attendanceService.bePresent(dto);

        if(response == -1) return new ResponseEntity(response, HttpStatus.ACCEPTED);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/finish")
    ResponseEntity finishAttendance(@RequestHeader("AccessToken") String accessToken,
                          @RequestBody long attendanceId){
        long response = attendanceService.finishAttendance(attendanceId);

        if(response == -1) return new ResponseEntity(response, HttpStatus.ACCEPTED);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/timeLeft/{groupId}")
    ResponseEntity getTimeLeft(@RequestHeader("AccessToken") String accessToken,
                          @PathVariable("groupId") long groupId){

        long response = attendanceService.getRemainingTime(groupId);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/check/{attendanceId}")
    ResponseEntity checkAttendance(@RequestHeader("AccessToken") String accessToken,
                          @PathVariable("attendanceId") long attendanceId,
                          @RequestBody AttendanceDto dto){

        long response = attendanceService.checkAttendance(attendanceId, dto);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
