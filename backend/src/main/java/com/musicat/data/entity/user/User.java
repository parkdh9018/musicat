package com.musicat.data.entity.user;


import com.musicat.data.entity.item.Background;
import com.musicat.data.entity.item.Badge;
import com.musicat.data.entity.item.Theme;
import com.musicat.data.entity.notice.Notice;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_seq")
  private long userSeq;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "user_nickname")
  private String userNickname;

  @Column(name = "user_profile_image")
  private String userProfileImage;

  @Column(name = "user_email")
  private String userEmail = "";

  @CreatedDate
  @Builder.Default
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "user_created_at")
  private LocalDateTime userCreatedAt = LocalDateTime.now();

  @Column(name = "user_money")
  private long userMoney = 0;

  // MoneyLog 와 1:N 연관관계 설정
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<MoneyLog> userMoneyLogList = new ArrayList<>();

  // Notice 와 1:N 연관관계 설정
  @OneToMany(mappedBy = "user")
  private List<Notice> userNoticeList = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "user_authority",
      joinColumns = {@JoinColumn(name = "user_seq", referencedColumnName = "user_seq")},
      inverseJoinColumns = {
          @JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
  private Set<Authority> userAuthority;

  @Column(name = "user_warn_count")
  private int userWarnCount = 0;

  /**
   * 아이템 3종
   * 뱃지, 배경, 테마
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "badge_seq")
  private Badge badge;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "background_seq")
  private Background background;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "theme_seq")
  private Theme theme;

  /**
   * boolean type getter는 is~ 로 구현된다. isUserIsDarkmode
   */
  @Column(name = "user_is_darkmode")
  private boolean userIsDarkmode = true;

  @Column(name = "user_is_Chatting_ban")
  private boolean userIsChattingBan = false;

  @Column(name = "user_is_ban")
  private boolean userIsBan = false;

  @Column(name = "user_is_user")
  private boolean userIsUser = false;


}
