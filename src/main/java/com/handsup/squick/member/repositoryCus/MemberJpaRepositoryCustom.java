package com.handsup.squick.member.repositoryCus;

import com.handsup.squick.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepositoryCustom {
    Optional<Member> findByName(String name);
    Optional<Member> findById(Long id);
    List<Member> findByGroupId(Long id);
    Optional<Member> findByNameAndGroupId(String name, Long groupId);
}
