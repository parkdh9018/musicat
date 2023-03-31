package com.musicat.data.repository.user;

import com.musicat.data.entity.user.User;
import com.musicat.data.entity.user.UserAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface UserAttendanceRepository extends JpaRepository<UserAttendance, Long> {

  Optional<UserAttendance> findByUserAndDate(User user, LocalDate date);
}
