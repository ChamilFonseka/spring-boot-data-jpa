package dev.chafon.datajpa.resource;

import dev.chafon.datajpa.BaseEntity;
import dev.chafon.datajpa.lecture.Lecture;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "resources")
@EntityListeners(AuditingEntityListener.class)
public class Resource extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int size;

  @Column(nullable = false)
  private String url;

  @OneToOne(mappedBy = "resource")
  private Lecture lecture;
}
