package com.handsup.squick.ServiceTest;

import com.handsup.squick.Dto.GroupDto.Attend.AttendCountDto;
import com.handsup.squick.Entity.SubAttendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.SubAttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import com.handsup.squick.Service.AttendanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;

@Transactional
@ExtendWith(MockitoExtension.class)
public class SubAttendanceServiceTest {
    @InjectMocks
    private AttendanceService attendanceService;

    @Mock
    private GroupJpaRepository groupJpaRepository;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private MemberGroupJpaRepository memberGroupJpaRepository;

    @Mock
    private SubAttendanceJpaRepository subAttendanceJpaRepository;

    @Test
    void getMonthMemberAttendance(){
        LocalDate date1 = LocalDate.of(2023, 5, 5);
        LocalDate date2 = LocalDate.of(2023, 4, 4);
        LocalDate date3 = LocalDate.of(2023, 7, 7);
        LocalDate date4 = LocalDate.of(2023, 7, 8);
        long id = 1L;

        Group group = Group.builder()
                .groupId(id)
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member member = Member.builder()
                .memberId(id)
                .memberName("Member")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        SubAttendance subAttendance1 = SubAttendance.builder()
                .subAttandanceId(1L)
                .day(0)
                .date(date1)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance subAttendance2 = SubAttendance.builder()
                .subAttandanceId(2L)
                .day(0)
                .date(date2)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ABSENT)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance subAttendance3 = SubAttendance.builder()
                .subAttandanceId(3L)
                .day(0)
                .date(date4)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance subAttendance4 = SubAttendance.builder()
                .subAttandanceId(4L)
                .day(0)
                .date(date3)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        List<SubAttendance> list = new ArrayList<>();
        list.add(subAttendance1);
        list.add(subAttendance2);

        int year = date1.getYear();
        int month = date1.getMonthValue();

        given(subAttendanceJpaRepository.findMonthAttendanceByGroupIdAndMemberIdAndDate(id, id, year, month)).willReturn(list);

        List<SubAttendance> test = subAttendanceJpaRepository.findMonthAttendanceByGroupIdAndMemberIdAndDate(id, id, year, month);

        assertThat(subAttendance1.getSubAttandanceId(), is(equalTo(test.get(0).getSubAttandanceId())));
        assertThat(subAttendance2.getSubAttandanceId(), is(equalTo(test.get(1).getSubAttandanceId())));
    }

    @Test
    void getMemberDetail(){
        LocalDate date1 = LocalDate.of(2023, 5, 5);
        LocalDate date2 = LocalDate.of(2023, 4, 4);
        LocalDate date3 = LocalDate.of(2023, 7, 7);
        LocalDate date4 = LocalDate.of(2023, 7, 8);
        long id = 1L;

        Group group = Group.builder()
                .groupId(id)
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member member = Member.builder()
                .memberId(id)
                .memberName("Member")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        SubAttendance subAttendance1 = SubAttendance.builder()
                .subAttandanceId(1L)
                .day(0)
                .date(date1)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance subAttendance2 = SubAttendance.builder()
                .subAttandanceId(2L)
                .day(1)
                .date(date2)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ABSENT)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance subAttendance3 = SubAttendance.builder()
                .subAttandanceId(3L)
                .day(2)
                .date(date3)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance subAttendance4 = SubAttendance.builder()
                .subAttandanceId(4L)
                .day(3)
                .date(date4)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        List<SubAttendance> list = new ArrayList<>();
        list.add(subAttendance1);
        list.add(subAttendance2);
        list.add(subAttendance3);
        list.add(subAttendance4);

        AttendCountDto attendCountDto = AttendCountDto.builder()
                .attend(1)
                .absent(1)
                .late(2)
                .build();

        given(subAttendanceJpaRepository.findAllAttendanceByGroupIdAndMemberId(group.getGroupId(), member.getMemberId())).willReturn(list);

        AttendCountDto testDto = attendanceService.getMemberDetail(group.getGroupId(), member.getMemberId());

        assertThat(attendCountDto.getAttend(), is(equalTo(testDto.getAttend())));
        assertThat(attendCountDto.getAbsent(), is(equalTo(testDto.getAbsent())));
        assertThat(attendCountDto.getLate(), is(equalTo(testDto.getLate())));
    }

    @Test
    void updateMemberAttendance(){
        LocalDate date = LocalDate.now();
        long groupId = 1L;
        long memberId = 1L;
        String status = "ATTEND";

        SubAttendance subAttendance = SubAttendance.builder()
                .groupId(groupId)
                .memberId(memberId)
                .date(date)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE)
                .day(0)
                .build();


        switch(status){
            case "ATTEND":
                subAttendance.setAttendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND);
                break;
            case "ABSENT":
                subAttendance.setAttendanceStatus(SubAttendance.AttendanceStatus.STATUS_ABSENT);
                break;
            case "LATE":
                subAttendance.setAttendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE);
                break;
        }

        given(subAttendanceJpaRepository.save(subAttendance)).willReturn(subAttendance);

        SubAttendance testSubAttendance = subAttendanceJpaRepository.save(subAttendance);

        assertThat(testSubAttendance.getAttendanceStatus(), is(equalTo(SubAttendance.AttendanceStatus.STATUS_ATTEND)));
    }
}
