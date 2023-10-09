package com.handsup.squick.group.repositoryCus;

import com.handsup.squick.group.entity.Group;

import java.util.List;
import java.util.Optional;

public interface GroupJpaRepositoryCustom {
    Optional<Group> findByName(String name);
    Optional<Group> findById(Long Id);
    List<Group> findGroup();
}
