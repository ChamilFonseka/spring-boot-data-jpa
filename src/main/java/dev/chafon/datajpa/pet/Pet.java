package dev.chafon.datajpa.pet;

import dev.chafon.datajpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
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

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PetType type;

  public void update(PetDto petDto) {
    this.name = petDto.name();
    this.age = petDto.age();
    this.breed = petDto.breed();
  }
}
