package com.musicat.data.entity.user;


/*
사용자의 권한을 나타내는 엔티티
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authority")
public class Authority {

    @Id
    @Column(name = "authority_name")
    private String authorityName;

}

























