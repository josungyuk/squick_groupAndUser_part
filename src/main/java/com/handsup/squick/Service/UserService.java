package com.handsup.squick.Service;

import com.handsup.squick.Dao.GroupDao;
import com.handsup.squick.Dao.UserDao;
import com.handsup.squick.Dto.UserDto.UserAddDto;
import com.handsup.squick.Dto.UserDto.UserDeleteDto;
import com.handsup.squick.Dto.UserDto.UserExpulsionDto;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final GroupJpaRepository groupJpaRepository;

    public void userAdd(UserAddDto dto){
        UserDao userDao = UserDao.builder()
                .userId(dto.getUserId())
                .groupName(dto.getGroupName())
                .attendence(0)
                .lateness(0)
                .build();

        userJpaRepository.save(userDao);
    }

    public boolean userDelete(UserDeleteDto dto){
        if(dto.getUserId() != dto.getCheckUserId()) return false;

        userJpaRepository.deleteByUserIdAndGroupName(dto.getUserId(), dto.getGroupName());
        return true;
    }

    public boolean userExpulsion(UserExpulsionDto dto){
        GroupDao groupDao = groupJpaRepository.findGroupDaoByGroupName(dto.getGroupName()).remove(0);

        String hostId = groupDao.getHostId();
        String groupName = groupDao.getGroupName();

        if(hostId != dto.getHostId())
            return false;

        userJpaRepository.deleteByUserIdAndGroupName(dto.getUserId(), groupName);

        return true;
    }
}
