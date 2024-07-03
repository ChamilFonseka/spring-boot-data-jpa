package dev.chafon.datajpa.lecture;

import dev.chafon.datajpa.BaseEntity;
import dev.chafon.datajpa.resource.Resource;
import dev.chafon.datajpa.section.Section;
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
@Table(name = "lectures")
@EntityListeners(AuditingEntityListener.class)
public class Lecture extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "section_id")
  private Section section;

  @OneToOne
  @JoinColumn(name = "resource_id")
  private Resource resource;
}
