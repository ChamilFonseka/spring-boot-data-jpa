package dev.chafon.datajpa.pet.cat;

import dev.chafon.datajpa.owner.Owner;
import dev.chafon.datajpa.pet.Pet;
import dev.chafon.datajpa.pet.PetDto;
import dev.chafon.datajpa.pet.PetType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cats")
@DynamicUpdate
public class Cat extends Pet {
  private String registry;

  public static Cat of(PetDto petDto, Owner owner) {
    return Cat.builder()
        .name(petDto.name())
        .dateOfBirth(petDto.dateOfBirth())
        .breed(petDto.breed())
        .registry(petDto.registry())
        .type(PetType.CAT)
        .owner(owner)
        .build();
  }

  @Override
  public void update(PetDto petDto) {
    super.update(petDto);
    this.registry = petDto.registry();
  }
}
