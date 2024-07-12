package dev.chafon.datajpa.pet;

import java.time.LocalDate;

public record PetDto(
    Long id,
    String name,
    LocalDate dateOfBirth,
    String breed,
    String registry,
    String sound,
    String size,
    String coatLength,
    PetType type) {

  public static PetDto aCat(String name, LocalDate dateOfBirth, String breed, String registry) {
    return new PetDto(null, name, dateOfBirth, breed, registry, null, null, null, PetType.CAT);
  }

  public static PetDto aDog(
      String name,
      LocalDate dateOfBirth,
      String breed,
      String sound,
      String size,
      String coatLength) {
    return new PetDto(null, name, dateOfBirth, breed, null, sound, size, coatLength, PetType.DOG);
  }
}
