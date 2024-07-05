package dev.chafon.datajpa.resource;

import dev.chafon.datajpa.BaseEntity;
import dev.chafon.datajpa.lecture.Lecture;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Resource extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int size;

  @Column(nullable = false)
  private String url;

  @OneToOne(mappedBy = "resource")
  private Lecture lecture;
}
