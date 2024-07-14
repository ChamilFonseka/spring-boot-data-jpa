package dev.chafon.datajpa.pet.dog;

import dev.chafon.datajpa.owner.Owner;
import dev.chafon.datajpa.pet.Pet;
import dev.chafon.datajpa.pet.PetDto;
import dev.chafon.datajpa.pet.PetType;
import dev.chafon.datajpa.vet.Vet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dogs")
@DynamicUpdate
public class Dog extends Pet {
  @Column(nullable = false)
  private String sound;

  @Column(nullable = false)
  private String size;

  @Column(nullable = false)
  private String coatLength;

  public static Pet of(PetDto petDto, Owner owner, Set<Vet> vets) {
    return Dog.builder()
        .name(petDto.name())
        .dateOfBirth(petDto.dateOfBirth())
        .breed(petDto.breed())
        .sound(petDto.sound())
        .size(petDto.size())
        .coatLength(petDto.coatLength())
        .type(PetType.DOG)
        .owner(owner)
        .vets(vets)
        .build();
  }

  @Override
  public void update(PetDto petDto, Owner owner, Set<Vet> vets) {
    super.update(petDto, owner, vets);
    this.sound = petDto.sound();
    this.size = petDto.size();
    this.coatLength = petDto.coatLength();
  }
}
