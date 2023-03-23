package com.musicat.data.repository;

import com.musicat.data.entity.Alert;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    // userSeq를 기준으로 모든 알림 조회
    public Optional<List<Alert>> findAllByUserSeq(long userSeq);

    // userSeq를 기준으로 모든 알림 조회 (페이지네이션)
    public Page<Alert> findAllByUserSeq(long userSeq, Pageable pageable);

    // alertTitle을 기준으로 모든 알림 조회
    public Optional<List<Alert>> findAllByUserSeqAndAlertTitleContaining(long userSeq, String alertTitle);

    // alertContent를 기준으로 모든 알림 조회
    public Optional<List<Alert>> findAllByUserSeqAndAlertContentContaining(long userSeq, String alertContent);

    // alertTitle + alertContetnt 기준으로 모든 알림 조회
    public Optional<List<Alert>> findAllByUserSeqAndAlertTitleContainingOrAlertContentContaining(long userSeq, String alertTitle, String alertContent);

    // userSeq를 기준으로 안읽은 알림 개수 조회
    public long countByUserSeqAndAlertIsReadFalse(long userSeq);

}
