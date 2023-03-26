package com.musicat.data.entity.item;

import com.musicat.data.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*

아이템 3번 유형

 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="theme")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_seq")
    private long themeSeq;
    @Column(name = "theme_name")
    private String themeName;
    @Column(name = "theme_cost")
    private int themeCost;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private List<User> userList = new ArrayList<>();
}
