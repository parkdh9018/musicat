package com.musicat.data.entity.item;

import com.musicat.data.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/*

아이템 2번 유형

 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="background")
public class Background {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "background_seq")
    private long backgroundSeq;
    @Column(name = "background_name")
    private String backgroundName;
    @Column(name = "bacoground_cost")
    private int backgroundCost;

    @OneToMany(mappedBy = "background", cascade = CascadeType.ALL)
    private List<User> userList = new ArrayList<>();

}
