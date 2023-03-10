package com.handsup.squick.Dao;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "UserTable")
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long pk;
    String userId;
    String groupName;
    int attendence;
    int lateness;
}
