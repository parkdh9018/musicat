package com.musicat.data.entity.user;


import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @PrePersist
    public void perPersist() {
        this.moneyLogCreatedAt = LocalDateTime.now();
    }



}
