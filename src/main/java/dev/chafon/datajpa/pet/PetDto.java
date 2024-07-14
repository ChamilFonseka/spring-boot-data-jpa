package dev.chafon.datajpa.pet;

import java.time.LocalDate;
import java.util.Set;

public record PetDto(
    String name,
    LocalDate dateOfBirth,
    String breed,
    String registry,
    String sound,
    String size,
    String coatLength,
    PetType type,
    Long ownerId,
    Set<Long> vetIds) {

  public static PetDto aCat(
      String name,
      LocalDate dateOfBirth,
      String breed,
      String registry,
      Long ownerId,
      Set<Long> vetIds) {
    return new PetDto(
        name, dateOfBirth, breed, registry, null, null, null, PetType.CAT, ownerId, vetIds);
  }

  public static PetDto aDog(
      String name,
      LocalDate dateOfBirth,
      String breed,
      String sound,
      String size,
      String coatLength,
      Long ownerId,
      Set<Long> vetIds) {
    return new PetDto(
        name, dateOfBirth, breed, null, sound, size, coatLength, PetType.DOG, ownerId, vetIds);
  }
}
