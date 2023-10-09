//package com.handsup.squick.ServiceTest;
//
//import com.handsup.squick.group.dto.AttendCountDto;
//import com.handsup.squick.group.dto.AttendanceCreateDto;
//import com.handsup.squick.attendance.entity.MasterSubAttendance;
//import com.handsup.squick.attendance.entity.MasterAttendance;
//import com.handsup.squick.attendance.entity.SubAttendance;
//import com.handsup.squick.group.entity.Group;
//import com.handsup.squick.member.entity.Member;
//import com.handsup.squick.attendance.repository.MasterSubAttendanceJpaRepository;
//import com.handsup.squick.attendance.repository.MasterAttendanceJpaRepository;
//import com.handsup.squick.attendance.repository.SubAttendanceJpaRepository;
//import com.handsup.squick.group.repository.GroupJpaRepository;
//import com.handsup.squick.group.repository.MemberGroupJpaRepository;
//import com.handsup.squick.member.repository.MemberJpaRepository;
//import com.handsup.squick.attendance.service.AttendanceService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import javax.transaction.Transactional;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//@Transactional
//@ExtendWith(MockitoExtension.class)
//public class AttendanceServiceTest {
//    @InjectMocks
//    private AttendanceService attendanceService;
//
//    @Mock
//    private GroupJpaRepository groupJpaRepository;
//
//    @Mock
//    private MemberJpaRepository memberJpaRepository;
//
//    @Mock
//    private MemberGroupJpaRepository memberGroupJpaRepository;
//
//    @Mock
//    private SubAttendanceJpaRepository subAttendanceJpaRepository;
//
//    @Mock
//    private MasterAttendanceJpaRepository masterAttendanceJpaRepository;
//
//    @Mock
//    private MasterSubAttendanceJpaRepository masterSubAttendanceJpaRepository;
//
//    @Test
//    void getMonthMemberAttendance(){
//        LocalDate date1 = LocalDate.of(2023, 5, 5);
//        LocalDate date2 = LocalDate.of(2023, 4, 4);
//        LocalDate date3 = LocalDate.of(2023, 7, 7);
//        LocalDate date4 = LocalDate.of(2023, 7, 8);
//        long id = 1L;
//
//        Group group = Group.builder()
//                .groupId(id)
//                .groupName("TestGroup")
//                .description("blabla")
//                .masterName("TestMember")
//                .img("img")
//                .invitationCode("aaaaaa")
//                .build();
//
//        Member member = Member.builder()
//                .memberId(id)
//                .memberName("Member")
//                .isPin(true)
//                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
//                .email("test@test.com")
//                .img("TestImg")
//                .build();
//
//        SubAttendance subAttendance1 = SubAttendance.builder()
//                .subAttandanceId(1L)
//                .day(0)
//                .date(date1)
//                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
//                .groupId(group.getGroupId())
//                .memberId(member.getMemberId())
//                .build();
//
//        SubAttendance subAttendance2 = SubAttendance.builder()
//                .subAttandanceId(2L)
//                .day(0)
//                .date(date2)
//                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ABSENT)
//                .groupId(group.getGroupId())
//                .memberId(member.getMemberId())
//                .build();
//
//        SubAttendance subAttendance3 = SubAttendance.builder()
//                .subAttandanceId(3L)
//                .day(0)
//                .date(date4)
//                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE)
//                .groupId(group.getGroupId())
//                .memberId(member.getMemberId())
//                .build();
//
//        SubAttendance subAttendance4 = SubAttendance.builder()
//                .subAttandanceId(4L)
//                .day(0)
//                .date(date3)
//                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE)
//                .groupId(group.getGroupId())
//                .memberId(member.getMemberId())
//                .build();
//
//        List<SubAttendance> list = new ArrayList<>();
//        list.add(subAttendance1);
//        list.add(subAttendance2);
//
//        int year = date1.getYear();
//        int month = date1.getMonthValue();
//
//        given(subAttendanceJpaRepository.findMonthAttendanceByGroupIdAndMemberIdAndDate(id, id, year, month)).willReturn(list);
//
//        List<SubAttendance> test = subAttendanceJpaRepository.findMonthAttendanceByGroupIdAndMemberIdAndDate(id, id, year, month);
//
//        assertThat(subAttendance1.getSubAttandanceId(), is(equalTo(test.get(0).getSubAttandanceId())));
//        assertThat(subAttendance2.getSubAttandanceId(), is(equalTo(test.get(1).getSubAttandanceId())));
//    }
//
//    @Test
//    void getMemberDetail(){
//        LocalDate date1 = LocalDate.of(2023, 5, 5);
//        LocalDate date2 = LocalDate.of(2023, 4, 4);
//        LocalDate date3 = LocalDate.of(2023, 7, 7);
//        LocalDate date4 = LocalDate.of(2023, 7, 8);
//        long id = 1L;
//
//        Group group = Group.builder()
//                .groupId(id)
//                .groupName("TestGroup")
//                .description("blabla")
//                .masterName("TestMember")
//                .img("img")
//                .invitationCode("aaaaaa")
//                .build();
//
//        Member member = Member.builder()
//                .memberId(id)
//                .memberName("Member")
//                .isPin(true)
//                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
//                .email("test@test.com")
//                .img("TestImg")
//                .build();
//
//        SubAttendance subAttendance1 = SubAttendance.builder()
//                .subAttandanceId(1L)
//                .day(0)
//                .date(date1)
//                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
//                .groupId(group.getGroupId())
//                .memberId(member.getMemberId())
//                .build();
//
//        SubAttendance subAttendance2 = SubAttendance.builder()
//                .subAttandanceId(2L)
//                .day(1)
//                .date(date2)
//                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ABSENT)
//                .groupId(group.getGroupId())
//                .memberId(member.getMemberId())
//                .build();
//
//        SubAttendance subAttendance3 = SubAttendance.builder()
//                .subAttandanceId(3L)
//                .day(2)
//                .date(date3)
//                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE)
//                .groupId(group.getGroupId())
//                .memberId(member.getMemberId())
//                .build();
//
//        SubAttendance subAttendance4 = SubAttendance.builder()
//                .subAttandanceId(4L)
//                .day(3)
//                .date(date4)
//                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE)
//                .groupId(group.getGroupId())
//                .memberId(member.getMemberId())
//                .build();
//
//        List<SubAttendance> list = new ArrayList<>();
//        list.add(subAttendance1);
//        list.add(subAttendance2);
//        list.add(subAttendance3);
//        list.add(subAttendance4);
//
//        AttendCountDto attendCountDto = AttendCountDto.builder()
//                .attend(1)
//                .absent(1)
//                .late(2)
//                .build();
//
//        given(subAttendanceJpaRepository.findAllAttendanceByGroupIdAndMemberId(group.getGroupId(), member.getMemberId())).willReturn(list);
//
//        AttendCountDto testDto = attendanceService.getMemberDetail(group.getGroupId(), member.getMemberId());
//
//        assertThat(attendCountDto.getAttend(), is(equalTo(testDto.getAttend())));
//        assertThat(attendCountDto.getAbsent(), is(equalTo(testDto.getAbsent())));
//        assertThat(attendCountDto.getLate(), is(equalTo(testDto.getLate())));
//    }
//
//    @Test
//    void updateMemberAttendance(){
//        //given
//        LocalDate date = LocalDate.now();
//        long groupId = 1L;
//        long memberId = 1L;
//        String status = "ATTEND";
//
//        SubAttendance subAttendance = SubAttendance.builder()
//                .groupId(groupId)
//                .memberId(memberId)
//                .date(date)
//                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE)
//                .day(0)
//                .build();
//
//
//        switch(status){
//            case "ATTEND":
//                subAttendance.setAttendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND);
//                break;
//            case "ABSENT":
//                subAttendance.setAttendanceStatus(SubAttendance.AttendanceStatus.STATUS_ABSENT);
//                break;
//            case "LATE":
//                subAttendance.setAttendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE);
//                break;
//        }
//
//        given(subAttendanceJpaRepository.save(subAttendance)).willReturn(subAttendance);
//
//        //when
//        SubAttendance testSubAttendance = subAttendanceJpaRepository.save(subAttendance);
//
//        //then
//        assertThat(testSubAttendance.getAttendanceStatus(), is(equalTo(SubAttendance.AttendanceStatus.STATUS_ATTEND)));
//    }
//
//    @Test
//    void createAttendanceTest(){
//        //given
//        MasterAttendance masterAttendance = MasterAttendance.builder()
//                .date(LocalDate.now())
//                .time(LocalTime.now())
//                .memberId(1L)
//                .groupId(1L)
//                .day(1)
//                .activation(true)
//                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
//                .latitude(0)
//                .longitude(0)
//                .build();
//
//        AttendanceCreateDto dto = AttendanceCreateDto.builder()
//                .groupName("testGroup")
//                .memberName("testMember")
//                .authCode("dsadsa")
//                .timeLeft(4)
//                .latitude(0)
//                .longitude(0)
//                .build();
//
//        Group group = Group.builder()
//                .groupId(1L).build();
//        Member member = Member.builder()
//                .memberId(1L)
//                .build();
//
//
//        given(groupJpaRepository.findGroupByGroupName("testGroup")).willReturn(group);
//        given(memberJpaRepository.findMemberByMemberName("testMember")).willReturn(member);
//        given(masterAttendanceJpaRepository.save(any(MasterAttendance.class))).willReturn(masterAttendance);
//
//
//        long id = attendanceService.createAttendance(dto);
//
//        assertThat(id, is(equalTo(0L)));
//    }
//
//    @Test
//    void bePresentTest(){
//        LocalDate date1 = LocalDate.of(2023, 5, 5);
//
//        Group group = Group.builder()
//                .groupId(1L)
//                .groupName("TestGroup")
//                .description("blabla")
//                .masterName("master")
//                .img("img")
//                .invitationCode("aaaaaa")
//                .build();
//
//        Member master = Member.builder()
//                .memberId(1L)
//                .memberName("master")
//                .isPin(true)
//                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
//                .email("test@test.com")
//                .img("TestImg")
//                .build();
//
//        Member member = Member.builder()
//                .memberId(2L)
//                .memberName("Member")
//                .isPin(true)
//                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
//                .email("test@test.com")
//                .img("TestImg")
//                .build();
//
//        MasterAttendance masterAttendance = MasterAttendance.builder()
//                .masterAttandanceId(1L)
//                .memberId(1L)
//                .groupId(group.getGroupId())
//                .date(LocalDate.now())
//                .day(1)
//                .activation(true)
//                .latitude(37.7749)
//                .longitude(-122.4194)
//                .build();
//
//        List<MasterAttendance> attendances = new ArrayList<>();
//        attendances.add(masterAttendance);
//
//        SubAttendance subAttendance = SubAttendance.builder()
//                .subAttandanceId(35L)
//                .day(0)
//                .date(date1)
//                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
//                .groupId(group.getGroupId())
//                .memberId(member.getMemberId())
//                .build();
//
//        MasterSubAttendance masterSubAttendance = MasterSubAttendance.builder()
//                .masterAttendance(masterAttendance)
//                .subAttendance(subAttendance)
//                .build();
//
//        AttendanceCreateDto dto = AttendanceCreateDto.builder()
//                .groupName(group.getGroupName())
//                .memberName(member.getMemberName())
//                .timeLeft(5)
//                .authCode("000000")
//                .latitude(37.7760)
//                .longitude(-122.4190)
//                .build();
//
//        given(groupJpaRepository.findGroupByGroupName(any(String.class))).willReturn(group);
//        given(memberJpaRepository.findMemberByGroupIdAndMemberName(any(Long.class), any(String.class))).willReturn(master);
//        given(masterAttendanceJpaRepository.findMasterAttendanceByGroupIdAndMemberId(any(Long.class), any(Long.class))).willReturn(attendances);
//        given(memberJpaRepository.findMemberByMemberName(any(String.class))).willReturn(member);
//        given(subAttendanceJpaRepository.save(any(SubAttendance.class))).willReturn(subAttendance);
//
//        long testId = attendanceService.bePresent(dto);
//
//        assertThat(subAttendance.getSubAttandanceId(), is(equalTo(testId)));
//    }
//
//    @Test
//    void getRemainingTimeTest(){
//        LocalDate date = LocalDate.of(2023, 5, 5);
//
//        MasterAttendance masterAttendance = MasterAttendance.builder()
//                .masterAttandanceId(1L)
//                .memberId(1L)
//                .groupId(1L)
//                .date(LocalDate.now())
//                .day(1)
//                .time(LocalTime.now())
//                .activation(true)
//                .latitude(37.7749)
//                .longitude(-122.4194)
//                .build();
//
//        given(masterAttendanceJpaRepository.findMasterAttendanceByGroupIdAndDate(any(Long.class), any(LocalDate.class))).willReturn(masterAttendance);
//
//        System.out.println(attendanceService.getRemainingTime(1L));
//    }
//
//    @Test
//    void finishAttendanceTest(){
//        MasterAttendance masterAttendance = MasterAttendance.builder()
//                .masterAttandanceId(1L)
//                .memberId(1L)
//                .groupId(1L)
//                .date(LocalDate.now())
//                .day(1)
//                .time(LocalTime.now())
//                .activation(true)
//                .latitude(37.7749)
//                .longitude(-122.4194)
//                .build();
//
//        List<MasterAttendance> attendances = new LinkedList<>();
//        attendances.add(masterAttendance);
//        Page<MasterAttendance> page = new PageImpl<>(attendances);
//
//
//        given(masterAttendanceJpaRepository.findRecentMasterAttendanceByMasterGroupId(any(Long.class), any(Pageable.class))).willReturn(page);
//        given(masterAttendanceJpaRepository.save(masterAttendance)).willReturn(masterAttendance);
//
//        Long testId = attendanceService.finishAttendance(1L);
//
//        assertThat(masterAttendance.getMasterAttandanceId(), is(equalTo(testId)));
//    }
//
//
//}
