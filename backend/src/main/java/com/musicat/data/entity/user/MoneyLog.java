package com.musicat.data.entity.user;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "money_log ")
public class MoneyLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "money_log_seq")
  private long moneyLogSeq;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_seq")
  private User user;


  @Column(name = "money_log_type")
  private String moneyLogType;


  @Column(name = "money_log_detail")
  private String moneyLogDetail;


  @Column(name = "money_log_change")
  private long moneyLogChange;


  @CreatedDate
  @Builder.Default
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "money_log_created_at")
  private LocalDateTime moneyLogCreatedAt = LocalDateTime.now();


}
