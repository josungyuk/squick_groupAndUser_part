package com.handsup.squick.Repository;

import com.handsup.squick.Dao.UserDao;
import com.handsup.squick.Dto.UserDto.UserAddDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<UserDao, Long> {
    List<UserDao> findByGroupNameAndUserId(String groupName, String userId);

    List<UserDao> deleteByUserIdAndGroupName(String userName, String groupName);
}
