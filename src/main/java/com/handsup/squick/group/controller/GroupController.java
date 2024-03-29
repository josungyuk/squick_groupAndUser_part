package com.handsup.squick.group.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handsup.squick.attendance.dto.AttendanceUpdateDto;
import com.handsup.squick.group.dto.AttendCountDto;
import com.handsup.squick.group.dto.AttendStatus;
import com.handsup.squick.member.dto.MemberAddDto;
import com.handsup.squick.member.dto.MemberExpelDto;
import com.handsup.squick.member.dto.ParticipationDto;
import com.handsup.squick.attendance.entity.SubAttendance;
import com.handsup.squick.group.entity.Group;
import com.handsup.squick.group.dto.GroupCreateDto;
import com.handsup.squick.member.entity.Member;
import com.handsup.squick.attendance.service.AttendanceService;
import com.handsup.squick.group.service.GroupService;
import com.handsup.squick.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final MemberService memberService;
    private final AttendanceService attendanceService;

    @PostMapping(value = "/create")         //완
    public ResponseEntity createGroup(@RequestPart("dto") GroupCreateDto dto,
                                      @RequestPart("img") MultipartFile file){
        try {
            groupService.groupCreate(dto, file);
        }catch (IOException e){}

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/")            //완
    public ResponseEntity getGroups(){
        List<Group> response = groupService.groupRead();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/members/{groupId}")           //완
    public ResponseEntity getMember(@PathVariable("groupId") long groupId){
        List<Member> response = memberService.getMember(groupId);

        return new ResponseEntity(response, HttpStatus.OK);
    }


    @GetMapping("/participate/{groupId}")           //완
    public ResponseEntity getParticipate(@PathVariable("groupId") long groupId){
        List<Member> response = memberService.getWaitMember(groupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/participate")            //완
    public ResponseEntity participate(@RequestBody ParticipationDto dto){
        boolean isAccept = dto.isAccept();
        if(!isAccept)
            return new ResponseEntity<>(false, HttpStatus.OK);

        memberService.participate(dto);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    @GetMapping("/attendance/{groupId}")            //완
    public ResponseEntity getAttendanceStatus(@RequestParam("date") @JsonFormat(pattern = "yyyy-MM-dd") LocalDateTime date,
                                              @PathVariable("groupId") long groupId){
        HashMap<Integer, List<AttendStatus>> response = groupService.getAttendanceStatus(date, groupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/members/{groupId}")          //완
    public ResponseEntity joinGroup(@PathVariable("groupId") long groupId,
                                    @RequestBody MemberAddDto dto){
        boolean response = groupService.isVaildCode(groupId, dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{groupId}")           //완
    public ResponseEntity updateGroup(@PathVariable("groupId") long groupId,
                                 @RequestPart("dto") GroupCreateDto dto,
                                 @RequestPart("img") MultipartFile file){
        try {
            groupService.groupUpdate(dto, file, groupId);
        }catch (IOException e){}

        return ResponseEntity.ok("Modify Success");
    }

    @DeleteMapping("/members/expel")           //완
    public ResponseEntity expelMember(@RequestBody MemberExpelDto dto){
        memberService.expelMember(dto);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/members/pin/{memberId}")           //완
    public ResponseEntity setPin(@PathVariable("memberId") long memberId){

        memberService.setPin(memberId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/members/attendance/count")         //완
    public ResponseEntity getMemberDetail(@RequestParam("groupId") long groupId,
                                 @RequestParam("memberId") long memberId){
        AttendCountDto response = attendanceService.getMemberDetail(groupId, memberId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/members/attendance")           //완
    public ResponseEntity getMemberAttendance(@RequestParam("groupId") long groupId,
                                          @RequestParam("memberId") long memberId,
                                          @RequestParam("date") String date){
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        List<SubAttendance> response = attendanceService.getMonthMemberAttendance(groupId, memberId, localDate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/members/attendance/update")            //완
    public ResponseEntity updateMemberAttendance(@RequestBody AttendanceUpdateDto dto){
        boolean response = attendanceService.updateMemberAttendance(dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
