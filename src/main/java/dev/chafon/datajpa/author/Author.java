package dev.chafon.datajpa.author;

import dev.chafon.datajpa.BaseEntity;
import dev.chafon.datajpa.course.Course;
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
@Table(name = "authors")
public class Author extends BaseEntity {

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  @ManyToMany(mappedBy = "authors")
  private Set<Course> courses;
}
