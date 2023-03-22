package com.musicat.data.entity.user;


/*
사용자의 권한을 나타내는 엔티티
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "authority")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Authority {

    @Id
    @Column(name = "authority_name")
    private String authorityName;

}

























