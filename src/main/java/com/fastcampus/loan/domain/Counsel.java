package com.fastcampus.loan.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.websocket.server.ServerEndpoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader.Strategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class Counsel extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long counselId;

  @Column(nullable = false, columnDefinition = "datetime COMMENT '신청일자'")
  private LocalDateTime appliedAt;

  @Column(nullable = false, columnDefinition = "varchar(12) COMMENT '상담 요청자'")
  private String name;
  @Column(nullable = false, columnDefinition = "varchar(23) COMMENT '휴대전화번호'")
  private String cellPhone;
  @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT '상담 요청자 전자메일주소'")
  private String email;
  @Column(columnDefinition = "text DEFAULT NULL COMMENT '상담메모'")
  private String memo;
  @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT '주소'")
  private String address;
  @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT '상세주소'")
  private String addressDetail;
  @Column(columnDefinition = "varchar(5) DEFAULT NULL COMMENT '우편번호'")
  private String zipCode;
}
