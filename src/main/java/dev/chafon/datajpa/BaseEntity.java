package dev.chafon.datajpa;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
  @Id @GeneratedValue private Long id;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Instant createdDate;

  @LastModifiedDate
  @Column(insertable = false)
  private Instant lastModifiedDate;
}
