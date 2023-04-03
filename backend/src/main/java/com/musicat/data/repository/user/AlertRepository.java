package com.musicat.data.repository.user;

import com.musicat.data.entity.user.Alert;
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


  // alertTitle + alertContetnt 기준으로 모든 알림 조회
  public Page<Alert> findAllByUserSeqAndAlertTitleContainingAndAlertContentContaining(long userSeq,
      String alertTitle, String alertContent, Pageable pageable);

  // userSeq를 기준으로 안읽은 알림 개수 조회
  public long countByUserSeqAndAlertIsReadFalse(long userSeq);

}
