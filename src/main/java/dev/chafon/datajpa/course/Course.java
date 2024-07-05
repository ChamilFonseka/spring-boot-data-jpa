package dev.chafon.datajpa.course;

import dev.chafon.datajpa.BaseEntity;
import dev.chafon.datajpa.author.Author;
import dev.chafon.datajpa.section.Section;
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
@Table(name = "courses")
public class Course extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @ManyToMany
  @JoinTable(
      name = "courses_authors",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "author_id"))
  private Set<Author> authors;

  @OneToMany(mappedBy = "course")
  private Set<Section> sections;
}
