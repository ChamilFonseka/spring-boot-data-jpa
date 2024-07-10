package dev.chafon.datajpa.pet;

public record PetDto(
    Long id,
    String name,
    Integer age,
    String breed,
    String registry,
    String sound,
    String size,
    String coatLength) {

  public static PetDto aCat(String name, int age, String breed, String registry) {
    return new PetDto(null, name, age, breed, registry, null, null, null);
  }

  public static PetDto aDog(
      String name, int age, String breed, String sound, String size, String coatLength) {
    return new PetDto(null, name, age, breed, null, sound, size, coatLength);
  }
}
