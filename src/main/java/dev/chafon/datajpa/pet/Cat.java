package dev.chafon.datajpa.pet;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cats")
public class Cat extends Pet {
  private String registry;

  public static Cat of(PetDto petDto) {
    return Cat.builder()
        .name(petDto.name())
        .age(petDto.age())
        .breed(petDto.breed())
        .registry(petDto.registry())
        .build();
  }
}
