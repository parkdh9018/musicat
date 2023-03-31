package com.musicat.data.repository.user;

import com.musicat.data.entity.user.MoneyLog;
import com.musicat.data.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyLogRepository extends JpaRepository<MoneyLog, Long> {

  Page<MoneyLog> findByUser(User user, Pageable pageable);

}
