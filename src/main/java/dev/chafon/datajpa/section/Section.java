package dev.chafon.datajpa.section;

import dev.chafon.datajpa.BaseEntity;
import dev.chafon.datajpa.course.Course;
import dev.chafon.datajpa.lecture.Lecture;
import jakarta.persistence.*;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "sections")
public class Section extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int sectionOrder;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @OneToMany(mappedBy = "section")
  private Set<Lecture> lectures;
}
