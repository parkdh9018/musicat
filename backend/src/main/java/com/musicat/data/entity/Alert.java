package com.musicat.data.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Alert {

    @Column(name = "alert_seq")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long alertSeq;

    @Column(name = "user_seq")
    private long userSeq;

    @Column(name = "alert_title")
    private String alertTitle;

    @Column(name = "alert_content")
    private String alertContent;

    @Column(name = "alert_created_at")
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime alertCreatedAt;

    @Column(name = "alert_is_read")
    private boolean alertIsRead;


    @PrePersist
    public void prePersist() {
        this.alertCreatedAt = LocalDateTime.now();
    }


}
