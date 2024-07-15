package dev.chafon.datajpa;

import dev.chafon.datajpa.owner.Address;
import dev.chafon.datajpa.owner.Owner;
import dev.chafon.datajpa.owner.OwnerDto;
import dev.chafon.datajpa.pet.PetDto;
import dev.chafon.datajpa.pet.PetType;
import dev.chafon.datajpa.pet.cat.Cat;
import dev.chafon.datajpa.pet.dog.Dog;
import dev.chafon.datajpa.vet.Vet;
import dev.chafon.datajpa.vet.VetDto;
import java.util.HashSet;
import java.util.Set;
import net.datafaker.Faker;

public class TestUtil {

  private static final Faker faker = new Faker();

  public static Owner generateFakeOwner() {
    return Owner.builder()
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .phoneNumber(faker.phoneNumber().phoneNumber())
        .address(
            new Address(
                faker.address().streetAddress(),
                faker.address().city(),
                faker.address().stateAbbr(),
                faker.address().zipCode()))
        .build();
  }

  public static OwnerDto generateFakeOwnerDto() {
    return new OwnerDto(
        faker.name().firstName(),
        faker.name().lastName(),
        faker.phoneNumber().phoneNumber(),
        faker.address().streetAddress(),
        faker.address().city(),
        faker.address().stateAbbr(),
        faker.address().zipCode());
  }

  public static Dog generateFakeDog(Owner owner, Set<Vet> vets) {
    return Dog.builder()
        .name(faker.dog().name())
        .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
        .breed(faker.dog().breed())
        .sound(faker.dog().sound())
        .size(faker.dog().size())
        .coatLength(faker.dog().coatLength())
        .type(PetType.DOG)
        .owner(owner)
        .vets(new HashSet<>(vets))
        .build();
  }

  public static PetDto generateFakeDogDto(Long ownerId) {
    return PetDto.aDog(
        faker.dog().name(),
        faker.date().birthdayLocalDate(1, 15),
        faker.dog().breed(),
        faker.dog().sound(),
        faker.dog().size(),
        faker.dog().coatLength(),
        ownerId,
        Set.of());
  }

  public static Cat generateFakeCat(Owner owner, Set<Vet> vets) {
    return Cat.builder()
        .name(faker.cat().name())
        .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
        .breed(faker.cat().breed())
        .registry(faker.cat().registry())
        .type(PetType.CAT)
        .owner(owner)
        .vets(new HashSet<>(vets))
        .build();
  }

  public static PetDto generateFakeCatDto(Long ownerId) {
    return PetDto.aCat(
        faker.cat().name(),
        faker.date().birthdayLocalDate(1, 15),
        faker.cat().breed(),
        faker.cat().registry(),
        ownerId,
        Set.of());
  }

  public static Vet generateFakeVet() {
    return Vet.builder()
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .phoneNumber(faker.phoneNumber().phoneNumber())
        .build();
  }

  public static VetDto generateFakeVetDto() {
    return new VetDto(
        faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber());
  }
}
