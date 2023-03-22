package com.musicat.data.repository;

import com.musicat.data.entity.Alert;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    // userSeq를 기준으로 모든 알림 조회
    public Optional<List<Alert>> findAllByUserSeq(long userSeq);

}
