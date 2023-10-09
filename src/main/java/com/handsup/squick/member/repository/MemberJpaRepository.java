package com.handsup.squick.member.repository;

import com.handsup.squick.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

}
