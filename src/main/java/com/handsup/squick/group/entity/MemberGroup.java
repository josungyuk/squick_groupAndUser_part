package com.handsup.squick.group.entity;

import com.handsup.squick.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Member_Group")
public class MemberGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    Group group;
}
