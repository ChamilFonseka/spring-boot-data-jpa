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
public class Resource extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String url;

  @Enumerated(EnumType.STRING)
  private ResourceType resourceType;

  @OneToOne(mappedBy = "resource")
  private Lecture lecture;
}
