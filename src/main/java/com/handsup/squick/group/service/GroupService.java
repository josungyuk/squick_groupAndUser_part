package com.handsup.squick.group.service;

import com.handsup.squick.attendance.repositoryImpl.SubAttendanceJpaRepositoryImpl;
import com.handsup.squick.group.dto.AttendStatus;
import com.handsup.squick.group.repositoryImpl.GroupJpaRepositoryImpl;
import com.handsup.squick.member.dto.MemberAddDto;
import com.handsup.squick.attendance.entity.SubAttendance;
import com.handsup.squick.group.entity.Group;
import com.handsup.squick.group.entity.MemberGroup;
import com.handsup.squick.member.entity.Member;
import com.handsup.squick.group.dto.GroupCreateDto;
import com.handsup.squick.attendance.repository.SubAttendanceJpaRepository;
import com.handsup.squick.group.repository.GroupJpaRepository;
import com.handsup.squick.group.repository.MemberGroupJpaRepository;
import com.handsup.squick.member.repository.MemberJpaRepository;
import com.handsup.squick.member.repositoryImpl.MemberJpaRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService{
    private final GroupJpaRepository groupJpaRepository;
    private final GroupJpaRepositoryImpl groupJpaRepositoryImpl;
    private final MemberJpaRepository memberJpaRepository;
    private final MemberJpaRepositoryImpl memberJpaRepositoryImpl;
    private final SubAttendanceJpaRepository subAttendanceJpaRepository;
    private final SubAttendanceJpaRepositoryImpl subAttendanceJpaRepositoryImpl;
    private final MemberGroupJpaRepository memberGroupJpaRepository;

    public String getFileUrl(MultipartFile file) throws IOException{
        String contextType = file.getContentType();
        byte[] bytes = file.getBytes();

        return "data:" + contextType + ":base64," + Base64.getEncoder().encodeToString(bytes);
    }

    //테스트 완
    public List groupRead(){
        List<Group> groups = groupJpaRepositoryImpl.findGroup();

        return groups;
    }

    //테스트 완
    public String getCode(){
        int zero = 48;
        int nine = 57;
        Random random = new Random();

        String code = random.ints(zero, nine + 1)
                .limit(6)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return code;
    }

    //테스트 완
    public boolean isVaildCode(long groupId, MemberAddDto dto){
        Group group = groupJpaRepositoryImpl.findById(groupId)
                .orElseThrow(() -> new NullPointerException());
        String code = dto.getCode();

        if(!group.getInvitationCode().equals(code))
            return false;

        Member member = memberJpaRepositoryImpl.findByNameAndGroupId(dto.getMemberName(), groupId)
                .orElseThrow( () -> new NullPointerException());

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        memberGroupJpaRepository.save(memberGroup);

        return true;
    }

    //테스트 완
    public void groupCreate(GroupCreateDto dto, MultipartFile file) throws IOException{
        String imageUrl = getFileUrl(file);

        Group group = Group.builder()
                .groupName(dto.getGroupName())
                .description(dto.getDescription())
                .img(imageUrl)
                .invitationCode(getCode())
                .build();

        Member member = memberJpaRepositoryImpl.findByName(dto.getMemberName())
                .orElseThrow(() -> new NullPointerException());

        Member masterMember = Member.builder()
                .username(member.getUsername())
                .isPin(member.getIsPin())
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email(member.getEmail())
                .img(member.getImg())
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                        .member(masterMember)
                        .group(group)
                        .build();


        memberJpaRepository.save(masterMember);
        groupJpaRepository.save(group);
        memberGroupJpaRepository.save(memberGroup);
    }

    //테스트 완
    public void groupUpdate(GroupCreateDto dto, MultipartFile file, long groupId) throws IOException{
        String imageUrl = getFileUrl(file);

        Group group = groupJpaRepositoryImpl.findById(groupId)
                .orElseThrow(() -> new NullPointerException());
        group.setGroupName(dto.getGroupName());
        group.setDescription(dto.getDescription());
        group.setImg(imageUrl);

        groupJpaRepository.save(group);
    }

    //테스트 완
    public HashMap getAttendanceStatus(LocalDateTime date, long groupId){
        List<Member> members = memberJpaRepositoryImpl.findByGroupId(groupId);
        List<Long> membersId = members.stream().map(Member::getId).collect(Collectors.toList());

        HashMap<Integer, List<AttendStatus>> map = new HashMap<>();
        int idx = 0;
        for(long memberId : membersId){
            List<SubAttendance> subAttendances = subAttendanceJpaRepositoryImpl.findGroupAttendStatus(date, groupId, memberId);
            String memberName = memberJpaRepositoryImpl.findById(memberId)
                    .orElseThrow(() -> new NullPointerException())
                    .getUsername();

            List<AttendStatus> attendStatuses = new ArrayList<>();

            for(SubAttendance subAttendance : subAttendances) {
                String status;

                if(subAttendance.getAttendanceStatus() == SubAttendance.AttendanceStatus.STATUS_ATTEND)
                    status = "ATTEND";
                else if(subAttendance.getAttendanceStatus() == SubAttendance.AttendanceStatus.STATUS_ABSENT)
                    status = "ABSENT";
                else
                    status = "LATE";

                AttendStatus attendStatus = AttendStatus.builder()
                        .memberName(memberName)
                        .date(subAttendance.getCreated().toLocalDate())
                        .state(status)
                        .build();

                attendStatuses.add(attendStatus);
            }

            map.put(idx++, attendStatuses);
        }

        return map;
    }








}
