package com.handsup.squick.Service;

import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Dto.GroupDto.GroupCreateDto;
import com.handsup.squick.Dto.GroupDto.GroupDeleteDto;
import com.handsup.squick.Dto.GroupDto.GroupUpdateDto;
import com.handsup.squick.Mapper.GroupMapper;
import com.handsup.squick.Mapper.MemberMapper;
import com.handsup.squick.Repository.AttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.MemberGroupAttendanceJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService{
    private final GroupJpaRepository groupJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final AttendanceJpaRepository attendanceJpaRepository;
    private final MemberGroupAttendanceJpaRepository memberGroupAttendanceJpaRepository;
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

    public void groupCreate(GroupCreateDto dto, MultipartFile file) throws IOException{
        String imageUrl = getFileUrl(file);

        Group groupDao = Group.builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .img(imageUrl)
                        .build();

        groupJpaRepository.save(groupDao);
    }

    public void groupUpdate(GroupCreateDto dto, MultipartFile file, long groupId) throws IOException{
        String imageUrl = getFileUrl(file);

        Group group = groupJpaRepository.findByGroupId(groupId);
        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        group.setImg(imageUrl);

        groupJpaRepository.save(group);
    }

    public void expelMember(long memberId){
        memberJpaRepository.deleteById(memberId);
    }


    public List getMember(long groupId){
        List<Member> members = memberJpaRepository.findMemberDaoByGroupId(groupId);

        return members;
    }










}
