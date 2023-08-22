package com.handsup.squick.RepositoryTest;

import com.handsup.squick.group.dto.AttendStatus;
import com.handsup.squick.attendance.entity.MasterSubAttendance;
import com.handsup.squick.attendance.entity.MasterAttendance;
import com.handsup.squick.attendance.entity.SubAttendance;
import com.handsup.squick.group.entity.Group;
import com.handsup.squick.group.entity.GroupAttendance;
import com.handsup.squick.group.entity.MemberGroup;
import com.handsup.squick.member.entity.Member;
import com.handsup.squick.attendance.repository.MasterSubAttendanceJpaRepository;
import com.handsup.squick.attendance.repository.MasterAttendanceJpaRepository;
import com.handsup.squick.attendance.repository.SubAttendanceJpaRepository;
import com.handsup.squick.group.repository.GroupJpaRepository;
import com.handsup.squick.group.repository.GroupAttendanceJpaRepository;
import com.handsup.squick.group.repository.MemberGroupJpaRepository;
import com.handsup.squick.member.repository.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubAttendanceRepositoryTest {
    @Autowired
    GroupJpaRepository groupJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    MemberGroupJpaRepository memberGroupJpaRepository;

    @Autowired
    SubAttendanceJpaRepository subAttendanceJpaRepository;

    @Autowired
    GroupAttendanceJpaRepository groupAttendanceJpaRepository;

    @Autowired
    MasterSubAttendanceJpaRepository masterSubAttendanceJpaRepository;

    @Autowired
    MasterAttendanceJpaRepository masterAttendanceJpaRepository;

    @Test
    void findById(){
        LocalDate date = LocalDate.of(2023, 5, 5);
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

        SubAttendance subAttendance = SubAttendance.builder()
                .subAttandanceId(id)
                .day(0)
                .date(date)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance saveSA = subAttendanceJpaRepository.save(subAttendance);

        SubAttendance testSA = subAttendanceJpaRepository.findById(subAttendance.getSubAttandanceId());

        assertThat(subAttendance.getSubAttandanceId(), is(equalTo(testSA.getSubAttandanceId())));
    }

    @Test
    void findSubAttandanceByGroupIdAndMemberIdAndDateTest(){
        LocalDate date = LocalDate.of(2023, 5, 5);
        long id = 1L;

        Group group = Group.builder()
                .groupId(id)
                .groupName("TestGroup")
                .description("blabla")
                .masterName("master")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member master = Member.builder()
                .memberId(id)
                .memberName("master")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        Member member = Member.builder()
                .memberId(id)
                .memberName("member")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        MasterAttendance masterAttendance = MasterAttendance.builder()
                .masterAttandanceId(id)
                .day(0)
                .date(date)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(master.getMemberId())
                .build();


        SubAttendance subAttendance = SubAttendance.builder()
                .subAttandanceId(id)
                .day(0)
                .date(date)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        MasterSubAttendance masterSubAttendance = MasterSubAttendance.builder()
                .masterAttendance(masterAttendance)
                .subAttendance(subAttendance)
                .build();

        GroupAttendance groupAttendance = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance)
                .build();

        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember = memberJpaRepository.save(member);
        MemberGroup saveMG = memberGroupJpaRepository.save(memberGroup);
        MasterAttendance saveMA = masterAttendanceJpaRepository.save(masterAttendance);
        SubAttendance saveSA = subAttendanceJpaRepository.save(subAttendance);
        MasterSubAttendance saveMSA = masterSubAttendanceJpaRepository.save(masterSubAttendance);
        GroupAttendance saveGA = groupAttendanceJpaRepository.save(groupAttendance);

        SubAttendance testSubAttendance = subAttendanceJpaRepository.findSubAttandanceByGroupIdAndMemberIdAndDate(
                group.getGroupId(), member.getMemberId(), date);

        assertThat(subAttendance.getSubAttandanceId(), is(equalTo(testSubAttendance.getSubAttandanceId())));
    }

    @Test
    void findAllAttendanceByGroupIdAndMemberIdTest(){
        LocalDate date1 = LocalDate.of(2023, 5, 5);
        LocalDate date2 = LocalDate.of(2023, 5, 6);
        long id = 1L;

        Group group = Group.builder()
                .groupId(id)
                .groupName("TestGroup")
                .description("blabla")
                .masterName("master")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member master = Member.builder()
                .memberId(2L)
                .memberName("master")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        Member member = Member.builder()
                .memberId(id)
                .memberName("member")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        MemberGroup memberGroup1 = MemberGroup.builder()
                .member(master)
                .group(group)
                .build();

        MemberGroup memberGroup2 = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        MasterAttendance masterAttendance1 = MasterAttendance.builder()
                .day(0)
                .date(date1)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(master.getMemberId())
                .build();

        MasterAttendance masterAttendance2 = MasterAttendance.builder()
                .day(1)
                .date(date2)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(master.getMemberId())
                .build();

        SubAttendance subAttendance1 = SubAttendance.builder()
                .day(0)
                .date(date1)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance subAttendance2 = SubAttendance.builder()
                .day(1)
                .date(date2)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        MasterSubAttendance masterSubAttendance1 = MasterSubAttendance.builder()
                .subAttendance(subAttendance1)
                .masterAttendance(masterAttendance1)
                .build();

        MasterSubAttendance masterSubAttendance2 = MasterSubAttendance.builder()
                .subAttendance(subAttendance2)
                .masterAttendance(masterAttendance2)
                .build();

        GroupAttendance groupAttendance1 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance1)
                .build();

        GroupAttendance groupAttendance2 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance2)
                .build();



        //when
        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember1 = memberJpaRepository.save(member);
        Member saveMember2 = memberJpaRepository.save(master);
        MasterAttendance saveMA1 = masterAttendanceJpaRepository.save(masterAttendance1);
        MasterAttendance saveMA2 = masterAttendanceJpaRepository.save(masterAttendance2);
        SubAttendance saveSubAttendance1 = subAttendanceJpaRepository.save(subAttendance1);
        SubAttendance saveSubAttendance2 = subAttendanceJpaRepository.save(subAttendance2);

        MemberGroup saveMg1 = memberGroupJpaRepository.save(memberGroup1);
        MemberGroup saveMg2 = memberGroupJpaRepository.save(memberGroup2);
        MasterSubAttendance saveMSA1 = masterSubAttendanceJpaRepository.save(masterSubAttendance1);
        MasterSubAttendance saveMSA2 = masterSubAttendanceJpaRepository.save(masterSubAttendance2);
        GroupAttendance saveGroupAttendance1 = groupAttendanceJpaRepository.save(groupAttendance1);
        GroupAttendance saveGroupAttendance2 = groupAttendanceJpaRepository.save(groupAttendance2);

        List<SubAttendance> testSubAttendanceList = subAttendanceJpaRepository.findAllAttendanceByGroupIdAndMemberId(id, id);
        SubAttendance testSubAttendance1 = testSubAttendanceList.get(0);
        SubAttendance testSubAttendance2 = testSubAttendanceList.get(1);

        assertThat(testSubAttendance1.getMemberId(), is(equalTo(saveSubAttendance1.getMemberId())));
        assertThat(testSubAttendance1.getGroupId(), is(equalTo(saveSubAttendance1.getGroupId())));
        assertThat(testSubAttendance1.getDate(), is(equalTo(saveSubAttendance1.getDate())));

        assertThat(testSubAttendance2.getMemberId(), is(equalTo(saveSubAttendance2.getMemberId())));
        assertThat(testSubAttendance2.getGroupId(), is(equalTo(saveSubAttendance2.getGroupId())));
        assertThat(testSubAttendance2.getDate(), is(equalTo(saveSubAttendance2.getDate())));
    }

    @Test
    void findMonthAttendanceByGroupIdAndMemberIdAndDate(){
        LocalDate date1 = LocalDate.of(2023, 5, 5);
        LocalDate date2 = LocalDate.of(2023, 5, 6);
        LocalDate date3 = LocalDate.of(2023, 4, 4);
        LocalDate date4 = LocalDate.of(2023, 7, 7);
        long id = 1L;

        Group group = Group.builder()
                .groupId(id)
                .groupName("TestGroup")
                .description("blabla")
                .masterName("master")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member master = Member.builder()
                .memberId(2L)
                .memberName("master")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        Member member = Member.builder()
                .memberId(1L)
                .memberName("member")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        MasterAttendance masterAttendance1 = MasterAttendance.builder()
                .day(0)
                .date(date1)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(master.getMemberId())
                .build();

        MasterAttendance masterAttendance2 = MasterAttendance.builder()
                .day(2)
                .date(date2)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(master.getMemberId())
                .build();

        MasterAttendance masterAttendance3 = MasterAttendance.builder()
                .day(5)
                .date(date3)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(master.getMemberId())
                .build();

        MasterAttendance masterAttendance4 = MasterAttendance.builder()
                .day(7)
                .date(date4)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(master.getMemberId())
                .build();

        SubAttendance subAttendance1 = SubAttendance.builder()
                .day(0)
                .date(date1)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance subAttendance2 = SubAttendance.builder()
                .day(2)
                .date(date2)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance subAttendance3 = SubAttendance.builder()
                .day(5)
                .date(date3)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        SubAttendance subAttendance4 = SubAttendance.builder()
                .day(7)
                .date(date4)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        GroupAttendance groupAttendance1 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance1)
                .build();

        GroupAttendance groupAttendance2 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance2)
                .build();

        GroupAttendance groupAttendance3 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance3)
                .build();

        GroupAttendance groupAttendance4 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance4)
                .build();

        MasterSubAttendance masterSubAttendance1 = MasterSubAttendance.builder()
                .masterAttendance(masterAttendance1)
                .subAttendance(subAttendance1)
                .build();

        MasterSubAttendance masterSubAttendance2 = MasterSubAttendance.builder()
                .masterAttendance(masterAttendance2)
                .subAttendance(subAttendance2)
                .build();

        MasterSubAttendance masterSubAttendance3 = MasterSubAttendance.builder()
                .masterAttendance(masterAttendance3)
                .subAttendance(subAttendance3)
                .build();

        MasterSubAttendance masterSubAttendance4 = MasterSubAttendance.builder()
                .masterAttendance(masterAttendance4)
                .subAttendance(subAttendance4)
                .build();


        //when
        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember = memberJpaRepository.save(member);
        MemberGroup saveMg = memberGroupJpaRepository.save(memberGroup);
        MasterAttendance saveMA1 = masterAttendanceJpaRepository.save(masterAttendance1);
        MasterAttendance saveMA2 = masterAttendanceJpaRepository.save(masterAttendance2);
        MasterAttendance saveMA3 = masterAttendanceJpaRepository.save(masterAttendance3);
        MasterAttendance saveMA4 = masterAttendanceJpaRepository.save(masterAttendance4);
        SubAttendance saveSubAttendance1 = subAttendanceJpaRepository.save(subAttendance1);
        SubAttendance saveSubAttendance2 = subAttendanceJpaRepository.save(subAttendance2);
        SubAttendance saveSubAttendance3 = subAttendanceJpaRepository.save(subAttendance3);
        SubAttendance saveSubAttendance4 = subAttendanceJpaRepository.save(subAttendance4);
        MasterSubAttendance saveMSA1 = masterSubAttendanceJpaRepository.save(masterSubAttendance1);
        MasterSubAttendance saveMSA2 = masterSubAttendanceJpaRepository.save(masterSubAttendance2);
        MasterSubAttendance saveMSA3 = masterSubAttendanceJpaRepository.save(masterSubAttendance3);
        MasterSubAttendance saveMSA4 = masterSubAttendanceJpaRepository.save(masterSubAttendance4);
        GroupAttendance saveGroupAttendance1 = groupAttendanceJpaRepository.save(groupAttendance1);
        GroupAttendance saveGroupAttendance2 = groupAttendanceJpaRepository.save(groupAttendance2);
        GroupAttendance saveGroupAttendance3 = groupAttendanceJpaRepository.save(groupAttendance3);
        GroupAttendance saveGroupAttendance4 = groupAttendanceJpaRepository.save(groupAttendance4);

        List<SubAttendance> testSubAttendanceList = subAttendanceJpaRepository.findMonthAttendanceByGroupIdAndMemberIdAndDate(id, id, 2023, 5);
        SubAttendance testSA1 = testSubAttendanceList.get(0);
        SubAttendance testSA2 = testSubAttendanceList.get(1);

        assertThat(subAttendance1.getSubAttandanceId(), is(equalTo(testSA1.getSubAttandanceId())));
        assertThat(subAttendance1.getMemberId(), is(equalTo(testSA1.getMemberId())));
        assertThat(subAttendance1.getDate(), is(equalTo(testSA1.getDate())));

        assertThat(subAttendance2.getSubAttandanceId(), is(equalTo(testSA2.getSubAttandanceId())));
        assertThat(subAttendance2.getMemberId(), is(equalTo(testSA2.getMemberId())));
        assertThat(subAttendance2.getDate(), is(equalTo(testSA2.getDate())));
    }

    @Test
    void findGroupCurAttendStatusTest(){
        LocalDate date = LocalDate.of(2023, 5, 5);

        Group group = Group.builder()
                .groupName("TestGroup")
                .description("blabla")
                .masterName("master")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member master = Member.builder()
                .memberName("master")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        Member member = Member.builder()
                .memberName("member")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        MasterAttendance masterAttendance = MasterAttendance.builder()
                .masterAttandanceId(1L)
                .day(0)
                .date(date)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(master.getMemberId())
                .build();

        SubAttendance subAttendance = SubAttendance.builder()
                .day(0)
                .date(date)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupId(group.getGroupId())
                .memberId(member.getMemberId())
                .build();

        GroupAttendance groupAttendance = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance)
                .build();

        MasterSubAttendance masterSubAttendance = MasterSubAttendance.builder()
                .masterAttendance(masterAttendance)
                .subAttendance(subAttendance)
                .build();

        AttendStatus attendStatus = AttendStatus.builder()
                .memberName(member.getMemberName())
                .date(date)
                .state("ATTEND")
                .build();

        //when
        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember = memberJpaRepository.save(member);
        MemberGroup saveMG = memberGroupJpaRepository.save(memberGroup);
        MasterAttendance saveMA = masterAttendanceJpaRepository.save(masterAttendance);
        SubAttendance saveSA = subAttendanceJpaRepository.save(subAttendance);
        MasterSubAttendance saveMSA = masterSubAttendanceJpaRepository.save(masterSubAttendance);
        GroupAttendance saveGA = groupAttendanceJpaRepository.save(groupAttendance);

        long memberId = groupJpaRepository.findMemberIdByGroupId(group.getGroupId()).get(0);

        SubAttendance testSubAttendance = subAttendanceJpaRepository.findGroupCurAttendStatus(date, group.getGroupId(), memberId).get(0);

        LocalDate testTime = testSubAttendance.getDate();
        String testMemberName = memberJpaRepository.findMemberByMemberId(memberId).getMemberName();
        String status;

        if(testSubAttendance.getAttendanceStatus() == SubAttendance.AttendanceStatus.STATUS_ATTEND)
            status = "ATTEND";
        else if(testSubAttendance.getAttendanceStatus() == SubAttendance.AttendanceStatus.STATUS_ABSENT)
            status = "ABSENT";
        else
            status = "LATE";

        assertThat(attendStatus.getMemberName(), is(equalTo(testMemberName)));
        assertThat(attendStatus.getDate(), is(equalTo(testTime)));
        assertThat(attendStatus.getState(), is(equalTo(status)));
    }
}
