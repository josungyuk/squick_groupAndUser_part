//package com.handsup.squick.RepositoryTest;
//
//import com.handsup.squick.group.entity.Group;
//import com.handsup.squick.group.entity.GroupAttendance;
//import com.handsup.squick.group.entity.MemberGroup;
//import com.handsup.squick.attendance.entity.MasterAttendance;
//import com.handsup.squick.member.entity.Member;
//import com.handsup.squick.group.repository.GroupJpaRepository;
//import com.handsup.squick.group.repository.GroupAttendanceJpaRepository;
//import com.handsup.squick.attendance.repository.MasterSubAttendanceJpaRepository;
//import com.handsup.squick.group.repository.MemberGroupJpaRepository;
//import com.handsup.squick.attendance.repository.MasterAttendanceJpaRepository;
//import com.handsup.squick.member.repository.MemberJpaRepository;
//import com.handsup.squick.attendance.repository.SubAttendanceJpaRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.is;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class MasterRepositoryTest {
//    @Autowired
//    GroupJpaRepository groupJpaRepository;
//
//    @Autowired
//    MemberJpaRepository memberJpaRepository;
//
//    @Autowired
//    MemberGroupJpaRepository memberGroupJpaRepository;
//
//    @Autowired
//    SubAttendanceJpaRepository subAttendanceJpaRepository;
//
//    @Autowired
//    GroupAttendanceJpaRepository groupAttendanceJpaRepository;
//
//    @Autowired
//    MasterSubAttendanceJpaRepository masterSubAttendanceJpaRepository;
//
//    @Autowired
//    MasterAttendanceJpaRepository masterAttendanceJpaRepository;
//
//
//    @Test
//    void findMasterAttendanceByGroupIdAndMemberId(){
//        //given
//        LocalDate date = LocalDate.of(2023, 5, 5);
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
//                .memberName("TestMember")
//                .isPin(true)
//                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
//                .email("test@test.com")
//                .img("TestImg")
//                .build();
//
//        MemberGroup memberGroup =  MemberGroup.builder()
//                .group(group)
//                .member(member)
//                .build();
//
//        MasterAttendance masterAttendance = MasterAttendance.builder()
//                .masterAttandanceId(id)
//                .memberId(member.getMemberId())
//                .groupId(group.getGroupId())
//                .date(LocalDate.now())
//                .day(1)
//                .activation(true)
//                .build();
//
//        GroupAttendance groupAttendance = GroupAttendance.builder()
//                .masterAttendance(masterAttendance)
//                .group(group)
//                .build();
//
//        Group saveGroup = groupJpaRepository.save(group);
//        Member saveMwmber = memberJpaRepository.save(member);
//        MemberGroup saveMG = memberGroupJpaRepository.save(memberGroup);
//        MasterAttendance saveMA = masterAttendanceJpaRepository.save(masterAttendance);
//        GroupAttendance saveGA = groupAttendanceJpaRepository.save(groupAttendance);
//
//        //when
//        List<MasterAttendance> masterAttendances = masterAttendanceJpaRepository.findMasterAttendanceByGroupIdAndMemberId(group.getGroupId(), member.getMemberId());
//        MasterAttendance testMasterAttendance = masterAttendances.get(0);
//
//        //then
//        assertThat(masterAttendance.getGroupId(), is(equalTo(testMasterAttendance.getGroupId())));
//    }
//
//    @Test
//    void findMasterAttendanceByGroupNameAndDateTest(){
//        //given
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
//        MasterAttendance masterAttendance = MasterAttendance.builder()
//                .masterAttandanceId(id)
//                .memberId(id)
//                .groupId(group.getGroupId())
//                .date(LocalDate.now())
//                .day(1)
//                .activation(true)
//                .build();
//
//        GroupAttendance groupAttendance = GroupAttendance.builder()
//                .masterAttendance(masterAttendance)
//                .group(group)
//                .build();
//
//        Group saveGroup = groupJpaRepository.save(group);
//        MasterAttendance saveMA = masterAttendanceJpaRepository.save(masterAttendance);
//        GroupAttendance saveGA = groupAttendanceJpaRepository.save(groupAttendance);
//
//        //when
//        MasterAttendance testMA = masterAttendanceJpaRepository.findMasterAttendanceByGroupIdAndDate(group.getGroupId(), LocalDate.now());
//
//        //then
//        assertThat(masterAttendance.getMasterAttandanceId(), is(equalTo(testMA.getMasterAttandanceId())));
//    }
//
//    @Test
//    void findRecentMasterAttendanceByMasterAttandanceIdTest(){
//        //given
//        LocalDate date1 = LocalDate.of(2023, 5, 5);
//        LocalDate date2 = LocalDate.of(2023, 4, 4);
//
//        long id = 1L;
//
//        Group group = Group.builder()
//                .groupId(id)
//                .groupName("TestGroup")
//                .description("blabla")
//                .masterName("TestMember1")
//                .img("img")
//                .invitationCode("aaaaaa")
//                .build();
//
//        MasterAttendance masterAttendance1 = MasterAttendance.builder()
//                .masterAttandanceId(1L)
//                .memberId(id)
//                .groupId(group.getGroupId())
//                .date(date1)
//                .day(1)
//                .activation(true)
//                .build();
//
//        MasterAttendance masterAttendance2 = MasterAttendance.builder()
//                .masterAttandanceId(2L)
//                .memberId(id)
//                .groupId(group.getGroupId())
//                .date(date2)
//                .day(1)
//                .activation(true)
//                .build();
//
//        GroupAttendance groupAttendance1 = GroupAttendance.builder()
//                .masterAttendance(masterAttendance1)
//                .group(group)
//                .build();
//
//        GroupAttendance groupAttendance2 = GroupAttendance.builder()
//                .masterAttendance(masterAttendance2)
//                .group(group)
//                .build();
//
//        Group saveGroup = groupJpaRepository.save(group);
//        MasterAttendance saveMA1 = masterAttendanceJpaRepository.save(masterAttendance1);
//        MasterAttendance saveMA2 = masterAttendanceJpaRepository.save(masterAttendance2);
//        GroupAttendance saveGA1 = groupAttendanceJpaRepository.save(groupAttendance1);
//        GroupAttendance saveGA2 = groupAttendanceJpaRepository.save(groupAttendance2);
//
//        Pageable pageable = PageRequest.of(0, 1);
//
//        //when
//        Page<MasterAttendance> page = masterAttendanceJpaRepository.findRecentMasterAttendanceByMasterGroupId(group.getGroupId(), pageable);
//        MasterAttendance testMA = page.getContent().get(0);
//
//        //given
//        assertThat(date1, is(equalTo(testMA.getDate())));
//    }
//}
