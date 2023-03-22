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

    @NotNull
    @Column(name = "money_log_type")
    private String moneyLogType;

    @NotNull
    @Column(name = "money_log_detail")
    private String moneyLogDetail;

    @NotNull
    @Column(name = "money_log_change")
    private long moneyLogChange;

    @NotNull
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "money_log_created_at")
    private LocalDateTime moneyLogCreatedAt;

    @PrePersist
    public void perPersist() {
        this.moneyLogCreatedAt = LocalDateTime.now();
    }



}
