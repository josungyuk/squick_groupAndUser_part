package com.handsup.squick.Service;

import com.handsup.squick.Dao.GroupDao;
import com.handsup.squick.Dto.GroupDto.GroupCreateDto;
import com.handsup.squick.Dto.GroupDto.GroupDeleteDto;
import com.handsup.squick.Dto.GroupDto.GroupModifyDto;
import com.handsup.squick.Repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupJpaRepository groupJpaRepository;
    private final UserService userService;

    public void groupCreate(GroupCreateDto dto){
        GroupDao dao = GroupDao.builder()
                .groupName(dto.getGroupName())
                        .groupExplain(dto.getGroupExplain())
                                .hostId(dto.getHostId())
                                        .limitPerson(dto.getLimitPerson())
                                                .startGroupDate(LocalDate.now())
                                                        .build();
        groupJpaRepository.save(dao);
    }

    public boolean groupDelete(GroupDeleteDto dto){
        boolean isHost = isHost(dto.getGroupName(), dto.getHostId());

        if(isHost){

            GroupDao dao = groupJpaRepository.findGroupDaoByGroupName(dto.getGroupName()).remove(0);
            groupJpaRepository.deleteById(dao.getPk());
            return true;
        }

        return false;
    }

    public boolean groupModify(GroupModifyDto dto){
        if(isParentGroup(dto.getCurGroupName())){
                GroupDao dao = groupJpaRepository.findGroupDaoByGroupName(dto.getCurGroupName()).remove(0);

                dao.setGroupName(dto.getNewGroupName());
                dao.setGroupExplain(dto.getGroupExplain());
                dao.setLimitPerson(dto.getLimitPerson());

                groupJpaRepository.save(dao);
                return true;
        }
        return false;
    }

    public GroupDao groupRead(String groupName){
        GroupDao dao = groupJpaRepository.findGroupDaoByGroupName(groupName).remove(0);

        return dao;
    }

    public GroupDao getGroupHostId(String groupName){
        GroupDao list = groupJpaRepository.findDistinctByGroupName(groupName).remove(0);

        return list;
    }

    public boolean isHost(String groupName, String hostId){
        String userId = groupJpaRepository.findGroupDaoByGroupName(groupName).remove(0).getHostId();

        if(userId.equals(hostId)) return true;

        return false;
    }

    public boolean isParentGroup(String groupName){
        if(groupJpaRepository.findGroupDaoByGroupName(groupName) == null) return false;
        return true;
    }

}
