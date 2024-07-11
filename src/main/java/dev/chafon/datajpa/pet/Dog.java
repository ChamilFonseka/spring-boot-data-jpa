package dev.chafon.datajpa.pet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dogs")
public class Dog extends Pet {
  @Column(nullable = false)
  private String sound;

  @Column(nullable = false)
  private String size;

  @Column(nullable = false)
  private String coatLength;

  public static Pet of(PetDto petDto) {
    return Dog.builder()
        .name(petDto.name())
        .age(petDto.age())
        .breed(petDto.breed())
        .sound(petDto.sound())
        .size(petDto.size())
        .coatLength(petDto.coatLength())
        .type(PetType.DOG)
        .build();
  }
}
