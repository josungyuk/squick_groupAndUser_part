package com.handsup.squick.Controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handsup.squick.Dto.AttendanceDto.AttendanceUpdate;
import com.handsup.squick.Dto.GroupDto.Attend.AttendCountDto;
import com.handsup.squick.Dto.GroupDto.Attend.AttendStatus;
import com.handsup.squick.Dto.MemberDto.MemberAddDto;
import com.handsup.squick.Dto.MemberDto.MemberExpelDto;
import com.handsup.squick.Dto.MemberDto.Participatation.ParticipationDto;
import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Dto.GroupDto.GroupCreateDto;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Service.AttendanceService;
import com.handsup.squick.Service.GroupService;
import com.handsup.squick.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;
    private final MemberService memberService;
    private final AttendanceService attendanceService;

    @GetMapping("/")            //완
    public ResponseEntity getGroup(@RequestHeader("AccessToken") String accessToken){
        List<Group> response = groupService.groupRead();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/members/{groupId}")           //완
    public ResponseEntity getMember(@RequestHeader("AccessToken") String accessToken,
                                    @RequestParam("groupId") long groupId){

        List<Group> response = memberService.getMember(groupId);

        return new ResponseEntity(response, HttpStatus.OK);
    }


    @GetMapping("/participate/{groupId}")           //완
    public ResponseEntity getParticipate(@RequestHeader("AccessToken") String accessToken,
                                         @RequestParam("groupId") long groupId){
        List<Member> response = memberService.getWaitMember(groupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/participate")            //완
    public ResponseEntity participate(@RequestHeader("AccessToken") String accessToken,
                                      @RequestBody ParticipationDto dto){
        boolean isAccept = dto.isAccept();
        if(!isAccept)
            return new ResponseEntity<>(false, HttpStatus.OK);

        memberService.participate(dto);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    @GetMapping("/attendance/{groupId}")            //완
    public ResponseEntity getAttendanceStatus(@RequestHeader("AccessToken") String accessToken,
                                              @RequestParam("date") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                              @RequestParam("groupId") long groupId){
        HashMap<Integer, List<AttendStatus>> response = groupService.getAttendanceStatus(date, groupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/members/{groupId}")          //완
    public ResponseEntity joinGroup(@RequestHeader("AccessToken") String accessToken,
                                    @RequestParam("groupId") long groupId,
                                    @RequestBody MemberAddDto dto){
        boolean response = groupService.isVaildCode(groupId, dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/create")         //완
    public ResponseEntity createGroup(@RequestHeader("AccessToken") String accessToken,
                                 @RequestPart("dto") GroupCreateDto dto,
                                 @RequestPart("img") MultipartFile file){
        try {
            groupService.groupCreate(dto, file);
        }catch (IOException e){}

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/{groupId}")           //완
    public ResponseEntity updateGroup(@RequestHeader("AccessToken") String accessToken,
                                 @PathVariable("groupId") long groupId,
                                 @RequestPart("dto") GroupCreateDto dto,
                                 @RequestPart("img") MultipartFile file){
        try {
            groupService.groupUpdate(dto, file, groupId);
        }catch (IOException e){}

        return ResponseEntity.ok("Modify Success");
    }

    @DeleteMapping("/members/expel")           //완
    public ResponseEntity expelMember(@RequestHeader("AccessToken") String accessToken,
                                      @RequestBody MemberExpelDto dto){
        memberService.expelMember(dto);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("members/pin/{memberId}")           //완
    public ResponseEntity setPin(@RequestHeader("AccessToken") String accessToken,
                  @PathVariable("memberId") long memberId){

        memberService.setPin(memberId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("members/attendance/count")         //완
    public ResponseEntity getMemberDetail(@RequestHeader("AccessToken") String accessToken,
                                 @RequestParam("groupId") long groupId,
                                 @RequestParam("memberId") long memberId){
        AttendCountDto response = attendanceService.getMemberDetail(groupId, memberId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("members/attendance")           //완
    public ResponseEntity getMemberAttendance(@RequestHeader("AccessToken") String accessToken,
                                          @RequestParam("groupId") long groupId,
                                          @RequestParam("memberId") long memberId,
                                          @RequestParam("date") String date){
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        List<Attendance> response = attendanceService.getMemberAttendance(groupId, memberId, localDate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("members/attendance/update")            //완
    public ResponseEntity updateMemberAttendance(@RequestHeader("AccessToken") String accessToken,
                                                 @RequestBody AttendanceUpdate dto){
        boolean response = attendanceService.updateMemberAttendance(dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
