package com.handsup.squick.Service;

import com.handsup.squick.Dto.GroupDto.Attend.AttendStatus;
import com.handsup.squick.Dto.MemberDto.Participatation.ParticipationDto;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Dto.GroupDto.GroupCreateDto;
import com.handsup.squick.Mapper.GroupMapper;
import com.handsup.squick.Mapper.MemberMapper;
import com.handsup.squick.Repository.AttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupService{
    private final GroupJpaRepository groupJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final AttendanceJpaRepository attendanceJpaRepository;
    private final GroupMapper groupMapper;
    private final MemberMapper memberMapper;

    public String getFileUrl(MultipartFile file) throws IOException{
        String contextType = file.getContentType();
        byte[] bytes = file.getBytes();

        return "data:" + contextType + ":base64," + Base64.getEncoder().encodeToString(bytes);
    }

    public List groupRead(){
        List<Group> groups = groupJpaRepository.findGroup();

        return groups;
    }

    public String getCode(){
        int zero = 80;
        int nine = 89;
        Random random = new Random();

        String code = random.ints(zero, nine + 1)
                .limit(6)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return code;
    }

    public boolean isVaildCode(long groupId, String code){
        Group group = groupJpaRepository.findByGroupId(groupId);

        if(group.getInvitationCode().equals(code))
            return true;

        return false;
    }

    public List getWaitMember(long groupId){
        List<Member> members = memberJpaRepository.findMemberByGroupId(groupId);

        List<Member> acceptMembers = new ArrayList<>();

        for(Member member : members){
            if(member.getInvitationStatus() == Member.InvitationStatus.INVITATION_ACCEPT)
                acceptMembers.add(member);
        }

        return acceptMembers;
    }

    public void groupCreate(GroupCreateDto dto, MultipartFile file) throws IOException{
        String imageUrl = getFileUrl(file);

        Group group = Group.builder()
                .groupName(dto.getGroupName())
                .description(dto.getDescription())
                .img(imageUrl)
                .invitationCode(getCode())
                .build();

        Member member = memberJpaRepository.findMemberByMemberName(dto.getMemberName());

        Member masterMember = Member.builder()
                .memberName(member.getMemberName())
                .isPin(member.isPin())
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email(member.getEmail())
                .img(member.getImg())
                .build();

        memberJpaRepository.save(masterMember);
        groupJpaRepository.save(group);
    }

    public void groupUpdate(GroupCreateDto dto, MultipartFile file, long groupId) throws IOException{
        String imageUrl = getFileUrl(file);

        Group group = groupJpaRepository.findByGroupId(groupId);
        group.setGroupName(dto.getGroupName());
        group.setDescription(dto.getDescription());
        group.setImg(imageUrl);

        groupJpaRepository.save(group);
    }

    public HashMap getAttendanceStatus(LocalDate date, long groupId){
        List<Long> membersId = groupJpaRepository.findMemberByGroupId(groupId);

        HashMap<Integer, List<AttendStatus>> map = new HashMap<>();
        int idx = 0;
        for(long memberId : membersId){
            List<AttendStatus> attendStatuses = attendanceJpaRepository.findGroupCurAttendStatus(date, groupId, memberId);

            map.put(idx++, attendStatuses);
        }

        return map;
    }


    public void expelMember(long memberId){
        memberJpaRepository.deleteById(memberId);
    }


    public List getMember(long groupId){
        List<Member> members = memberJpaRepository.findMemberByGroupId(groupId);

        return members;
    }

    public void participate(long memberId){
        Member member = memberJpaRepository.findMemberByMemberId(memberId);
        member.setInvitationStatus(Member.InvitationStatus.INVITATION_ACCEPT);
    }








}
