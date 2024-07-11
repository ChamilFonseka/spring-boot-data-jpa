package dev.chafon.datajpa.pet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.chafon.datajpa.TestContainersConfiguration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestContainersConfiguration.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PetServiceTest {

  @Autowired private PetService petService;

  @Autowired private PetRepository petRepository;

  private Faker faker;

  @BeforeAll
  void beforeAll() {
    faker = new Faker();
  }

  @AfterEach
  void tearDown() {
    petRepository.deleteAll();
  }

  @Test
  void shouldReturnAllPets() {
    Cat savedCat = petRepository.save(generateFakeCat());
    Dog savedDog = petRepository.save(generateFakeDog());

    List<PetDto> allPets = petService.getPets();
    assertThat(allPets).isNotNull();
    assertThat(allPets).hasSize(2);

    Optional<PetDto> optionalCat =
        allPets.stream().filter(pet -> Objects.equals(pet.id(), savedCat.getId())).findAny();
    assertThat(optionalCat).isPresent();

    PetDto fetchedCat = optionalCat.get();
    assertThat(fetchedCat.id()).isEqualTo(savedCat.getId());
    assertThat(fetchedCat.name()).isEqualTo(savedCat.getName());
    assertThat(fetchedCat.age()).isEqualTo(savedCat.getAge());
    assertThat(fetchedCat.registry()).isEqualTo(savedCat.getRegistry());
    assertThat(fetchedCat.sound()).isNull();
    assertThat(fetchedCat.size()).isNull();
    assertThat(fetchedCat.coatLength()).isNull();
    assertThat(fetchedCat.type()).isEqualTo(PetType.CAT);

    Optional<PetDto> optionalDog =
        allPets.stream().filter(pet -> Objects.equals(pet.id(), savedDog.getId())).findAny();
    assertThat(optionalDog).isPresent();

    PetDto fetchedDog = optionalDog.get();
    assertThat(fetchedDog.id()).isEqualTo(savedDog.getId());
    assertThat(fetchedDog.name()).isEqualTo(savedDog.getName());
    assertThat(fetchedDog.age()).isEqualTo(savedDog.getAge());
    assertThat(fetchedDog.sound()).isEqualTo(savedDog.getSound());
    assertThat(fetchedDog.size()).isEqualTo(savedDog.getSize());
    assertThat(fetchedDog.coatLength()).isEqualTo(savedDog.getCoatLength());
    assertThat(fetchedDog.registry()).isNull();
    assertThat(fetchedDog.type()).isEqualTo(PetType.DOG);
  }

  @Test
  void shouldReturnSavedCatById() {
    Cat savedCat = petRepository.save(generateFakeCat());

    PetDto fetchedCat = petService.getPet(savedCat.getId());
    assertThat(fetchedCat.id()).isEqualTo(savedCat.getId());
    assertThat(fetchedCat.name()).isEqualTo(savedCat.getName());
    assertThat(fetchedCat.age()).isEqualTo(savedCat.getAge());
    assertThat(fetchedCat.registry()).isEqualTo(savedCat.getRegistry());
    assertThat(fetchedCat.sound()).isNull();
    assertThat(fetchedCat.size()).isNull();
    assertThat(fetchedCat.coatLength()).isNull();
    assertThat(fetchedCat.type()).isEqualTo(PetType.CAT);
  }

  @Test
  void shouldReturnSavedDogById() {
    Dog savedDog = petRepository.save(generateFakeDog());

    PetDto fetchedDog = petService.getPet(savedDog.getId());
    assertThat(fetchedDog.id()).isEqualTo(savedDog.getId());
    assertThat(fetchedDog.name()).isEqualTo(savedDog.getName());
    assertThat(fetchedDog.age()).isEqualTo(savedDog.getAge());
    assertThat(fetchedDog.sound()).isEqualTo(savedDog.getSound());
    assertThat(fetchedDog.size()).isEqualTo(savedDog.getSize());
    assertThat(fetchedDog.coatLength()).isEqualTo(savedDog.getCoatLength());
    assertThat(fetchedDog.registry()).isNull();
    assertThat(fetchedDog.type()).isEqualTo(PetType.DOG);
  }
  
  @Test
  void shouldThrowPetNotFoundException() {
    long id = 99L;
    assertThatThrownBy(() -> petService.getPet(id))
        .isInstanceOf(PetNotFoundException.class)
        .hasMessageContaining("Pet with id " + id + " not found");
  }

  @Test
  void shouldSaveACat() {
    PetDto catTobeSaved = generateFakeCatDto();

    Long id = petService.createPet(catTobeSaved);
    assertThat(id).isNotNull();

    Optional<Pet> petOptional = petRepository.findById(id);
    assertThat(petOptional).isPresent();

    Cat catFromDb = (Cat) petOptional.get();
    assertThat(catFromDb.getName()).isEqualTo(catTobeSaved.name());
    assertThat(catFromDb.getAge()).isEqualTo(catTobeSaved.age());
    assertThat(catFromDb.getBreed()).isEqualTo(catTobeSaved.breed());
    assertThat(catFromDb.getRegistry()).isEqualTo(catTobeSaved.registry());
    assertThat(catFromDb.getType()).isEqualTo(PetType.CAT);
  }

  @Test
  void shouldSaveADog() {
    PetDto dogTobeSaved = generateFakeDogDto();

    Long id = petService.createPet(dogTobeSaved);
    assertThat(id).isNotNull();

    Optional<Pet> petOptional = petRepository.findById(id);
    assertThat(petOptional).isPresent();

    Dog dogFromDb = (Dog) petOptional.get();
    assertThat(dogFromDb.getName()).isEqualTo(dogTobeSaved.name());
    assertThat(dogFromDb.getAge()).isEqualTo(dogTobeSaved.age());
    assertThat(dogFromDb.getBreed()).isEqualTo(dogTobeSaved.breed());
    assertThat(dogFromDb.getSound()).isEqualTo(dogTobeSaved.sound());
    assertThat(dogFromDb.getSize()).isEqualTo(dogTobeSaved.size());
    assertThat(dogFromDb.getCoatLength()).isEqualTo(dogTobeSaved.coatLength());
    assertThat(dogFromDb.getType()).isEqualTo(PetType.DOG);
  }

  private PetDto generateFakeDogDto() {
    net.datafaker.providers.base.Dog fakeDog = faker.dog();
    return PetDto.aDog(
        fakeDog.name(),
        faker.number().numberBetween(1, 10),
        fakeDog.breed(),
        fakeDog.sound(),
        fakeDog.size(),
        fakeDog.coatLength());
  }

  private Dog generateFakeDog() {
    net.datafaker.providers.base.Dog fakeDog = faker.dog();
    return Dog.builder()
        .name(fakeDog.name())
        .age(faker.number().numberBetween(1, 10))
        .breed(fakeDog.breed())
        .sound(fakeDog.sound())
        .size(fakeDog.size())
        .coatLength(fakeDog.coatLength())
        .type(PetType.DOG)
        .build();
  }

  private PetDto generateFakeCatDto() {
    net.datafaker.providers.base.Cat fakeCat = faker.cat();
    return PetDto.aCat(
        fakeCat.name(), faker.number().numberBetween(1, 10), fakeCat.breed(), fakeCat.registry());
  }

  private Cat generateFakeCat() {
    net.datafaker.providers.base.Cat fakeCat = faker.cat();
    return Cat.builder()
        .name(fakeCat.name())
        .age(faker.number().numberBetween(1, 10))
        .breed(fakeCat.breed())
        .registry(fakeCat.registry())
        .type(PetType.CAT)
        .build();
  }
}
