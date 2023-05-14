package com.handsup.squick.Entity;

import com.handsup.squick.Entity.JoinEntity.MemberAttendance;
import com.handsup.squick.Entity.JoinEntity.MemberGroup;
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
@Table(name = "Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    long memberId;

    @Column(name = "memberName")
    String memberName;

    @Column(name = "groupName")
    String groupName;

    @Column(name = "isPin")
    boolean isPin;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "invitationStatus")
    InvitationStatus invitationStatus = InvitationStatus.INVITATION_WAIT;
    @Column(name = "email")
    String email;
    @Column(name = "img", length = 65546)
    String img;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MemberAttendance> memberAttendances = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MemberGroup> memberGroups = new ArrayList<>();

    public enum InvitationStatus{
        INVITATION_ACCEPT("허가"),
        INVITATION_WAIT("대기중"),
        INVITATION_DENY("거부");

        @Getter
        @Setter
        private String status;

        InvitationStatus(String status) {
            this.status = status;
        }
    }
}
