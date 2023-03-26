package com.musicat.data.entity.item;


import com.musicat.data.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/*

아이템 1번 유형
하나의 아이템은 여러 개의 유저와 관계를 맺는다. 1:N

 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "badge")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_seq")
    private long badgeSeq;
    @Column(name = "badge_name")
    private String badgeName;
    @Column(name = "badge_cost")
    private int badgeCost;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL)
    private List<User> userList = new ArrayList<>();

}
