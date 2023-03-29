package com.musicat.data.entity.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_attendance")
public class UserAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_attendance_seq")
    private long userAttendanceSeq;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(name="user_attendance_date")
    private LocalDate date;





}
