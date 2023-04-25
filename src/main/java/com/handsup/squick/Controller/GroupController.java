package com.handsup.squick.Controller;

import com.handsup.squick.Dto.GroupDto.Attend.AttendCountDto;
import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Dto.GroupDto.GroupCreateDto;
import com.handsup.squick.Dto.GroupDto.GroupDeleteDto;
import com.handsup.squick.Dto.GroupDto.GroupUpdateDto;
import com.handsup.squick.Dto.GroupDto.GroupReadDto;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;
    private final MemberService memberService;

    @GetMapping("/")
    public ResponseEntity getGroup(@RequestHeader("AccessToken") String accessToken){
        List<Group> response = groupService.groupRead();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/members/{groupId}")
    public ResponseEntity getMember(@RequestHeader("AccessToken") String accessToken,
                                    @RequestParam("groupId") long groupId){

        List<Group> response = groupService.getMember(groupId);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    /*
    @GetMapping("/participate/{groupId}")
    public ResponseEntity getParticipate(@RequestHeader("AccessToken") String accessToken,
                                         @RequestParam("groupId") long groupId){
        groupService.
    }



    @PostMapping("/participate")
    public ResponseEntity participate(@RequestHeader("AccessToken") String accessToken,
                                      @RequestBody ParticipationDto dto){

    }



    @GetMapping("/attendance/{groupId}")
    public ResponseEntity getAttendanceStatus(@RequestHeader("AccessToken") String accessToken,
                                              @RequestParam("date") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                              @RequestParam("groupId") long groupId){
        List<Group> response = groupService.getAttendanceStatus(date, groupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping("/members/{groupId}")
    public ResponseEntity joinGroup(@RequestHeader("AccessToken") String accessToken,
                                    @RequestParam("groupId") long groupId,
                                    @RequestBody("code") String code){

*/
    @PostMapping("/create")
    public ResponseEntity createGroup(@RequestHeader("AccessToken") String accessToken,
                                 @RequestBody GroupCreateDto dto,
                                 @RequestParam MultipartFile file){
        try {
            groupService.groupCreate(dto, file);
        }catch (IOException e){}

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity updateGroup(@RequestHeader("AccessToken") String accessToken,
                                 @PathVariable("groupId") long groupId,
                                 @RequestBody GroupCreateDto dto,
                                 @RequestParam MultipartFile file){
        try {
            groupService.groupUpdate(dto, file, groupId);
        }catch (IOException e){}

        return ResponseEntity.ok("Modify Success");
    }
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity expelMember(@RequestHeader("AccessToken") String accessToken,
                                 @PathVariable long memberId){
        groupService.expelMember(memberId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /*

    @PutMapping("members/pin/{memberId}")
    public ResponseEntity setPin((@RequestHeader("AccessToken") String accessToken,
                  @PathVariable("memberId") long memberId){

        return new ResponseEntity<>(true, HttpStatus.OK);
    }


     */

    @GetMapping("members/attendance/count")
    public ResponseEntity getMemberDetail(@RequestHeader("AccessToken") String accessToken,
                                 @RequestParam("groupId") long groupId,
                                 @RequestParam("memberId") long memberId){
        AttendCountDto response = memberService.getMemberDetail(groupId, memberId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("members/attendance")
    public ResponseEntity getMemberAttendance(@RequestHeader("AccessToken") String accessToken,
                                          @RequestParam("groupId") long groupId,
                                          @RequestParam("memberId") long memberId,
                                          @RequestParam("date") String date){
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        List<Attendance> response = memberService.getMemberAttendance(groupId, memberId, localDate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("members/attendance/{attendanceId}")
    public ResponseEntity updateMemberAttendance(@RequestHeader("AccessToken") String accessToken,
                                          @PathVariable("attendanceId") long attendanceId,
                                          @RequestParam("status") String status){
        memberService.updateMemberAttendance(attendanceId, status);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
