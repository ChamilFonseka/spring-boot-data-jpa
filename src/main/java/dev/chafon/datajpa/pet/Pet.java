package dev.chafon.datajpa.pet;

import dev.chafon.datajpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pets")
public abstract class Pet extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int age;

  @Column(nullable = false)
  private String breed;
}
