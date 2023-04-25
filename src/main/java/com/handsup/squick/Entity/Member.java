package com.handsup.squick.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "UserTable")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long memberPk;
    @Column(name = "memberId")
    String memberId;
    @Column(name = "isPin")
    boolean isPin;
    @Column(name = "email")
    int email;
    @Column(name = "img")
    String img;

    @OneToMany(mappedBy = "member")
    List<MemberGroupAttendance> memberGroupAttendances = new ArrayList<>();
}
