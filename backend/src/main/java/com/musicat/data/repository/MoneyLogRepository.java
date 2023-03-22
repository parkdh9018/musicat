package com.musicat.data.repository;

import com.musicat.data.entity.user.MoneyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyLogRepository extends JpaRepository<MoneyLog, Long> {
}
