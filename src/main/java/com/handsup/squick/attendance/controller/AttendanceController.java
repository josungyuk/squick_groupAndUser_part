package com.handsup.squick.attendance.controller;

import com.handsup.squick.attendance.dto.AttendanceDto;
import com.handsup.squick.group.dto.AttendanceCreateDto;
import com.handsup.squick.attendance.service.AttendanceService;
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
    ResponseEntity createAttendance(@RequestBody AttendanceCreateDto dto){
        long response = attendanceService.createAttendance(dto);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PostMapping("/attend")
    ResponseEntity presentAttendance(@RequestBody AttendanceCreateDto dto){
        long response = attendanceService.bePresent(dto);

        if(response == -1) return new ResponseEntity(response, HttpStatus.ACCEPTED);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/finish")
    ResponseEntity finishAttendance(@RequestBody long groupId){
        long response = attendanceService.finishAttendance(groupId);

        if(response == -1) return new ResponseEntity(response, HttpStatus.ACCEPTED);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/timeLeft/{groupId}")
    ResponseEntity getTimeLeft(@PathVariable("groupId") long groupId){

        long response = attendanceService.getRemainingTime(groupId);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/check/{attendanceId}")
    ResponseEntity checkAttendance(@PathVariable("attendanceId") long attendanceId,
                          @RequestBody long groupId,
                          @RequestBody AttendanceDto dto){

        long response = attendanceService.checkAttendance(attendanceId, groupId, dto);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
