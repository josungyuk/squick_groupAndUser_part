package com.handsup.squick.Dao;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "GroupTable")
public class GroupDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long pk;
    @Column(name = "groupName")
    String groupName;
    @Column(name = "hostId")
    String hostId;
    @Column(name = "groupExplain")
    String groupExplain;
    @Column(name = "limitPerson")
    int limitPerson;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "startGroupDate")
    LocalDate startGroupDate;
}
