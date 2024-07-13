package dev.chafon.datajpa.pet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.chafon.datajpa.TestContainersConfiguration;
import dev.chafon.datajpa.pet.cat.Cat;
import dev.chafon.datajpa.pet.cat.CatRepository;
import dev.chafon.datajpa.pet.cat.CatView;
import dev.chafon.datajpa.pet.dog.Dog;
import dev.chafon.datajpa.pet.dog.DogRepository;
import dev.chafon.datajpa.pet.dog.DogView;
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

  @Autowired private TestPetRepository petRepository;
  @Autowired private CatRepository catRepository;
  @Autowired private DogRepository dogRepository;

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

    List<PetView> allPets = petService.getPets();
    assertThat(allPets).isNotNull();
    assertThat(allPets).hasSize(2);

    Optional<PetView> optionalCat =
        allPets.stream().filter(pet -> Objects.equals(pet.getId(), savedCat.getId())).findAny();
    assertThat(optionalCat).isPresent();

    PetView fetchedCat = optionalCat.get();
    assertThat(fetchedCat.getId()).isEqualTo(savedCat.getId());
    assertThat(fetchedCat.getName()).isEqualTo(savedCat.getName());
    assertThat(fetchedCat.getDateOfBirth()).isEqualTo(savedCat.getDateOfBirth());
    assertThat(fetchedCat.getBreed()).isEqualTo(savedCat.getBreed());
    assertThat(fetchedCat.getType()).isEqualTo(PetType.CAT);

    Optional<PetView> optionalDog =
        allPets.stream().filter(pet -> Objects.equals(pet.getId(), savedDog.getId())).findAny();
    assertThat(optionalDog).isPresent();

    PetView fetchedDog = optionalDog.get();
    assertThat(fetchedDog.getId()).isEqualTo(savedDog.getId());
    assertThat(fetchedDog.getName()).isEqualTo(savedDog.getName());
    assertThat(fetchedDog.getDateOfBirth()).isEqualTo(savedDog.getDateOfBirth());
    assertThat(fetchedDog.getBreed()).isEqualTo(savedDog.getBreed());
    assertThat(fetchedDog.getType()).isEqualTo(PetType.DOG);
  }

  @Test
  void shouldReturnSavedCatById() {
    Cat savedCat = petRepository.save(generateFakeCat());

    CatView fetchedCat = (CatView) petService.getPet(savedCat.getId());
    assertThat(fetchedCat.getId()).isEqualTo(savedCat.getId());
    assertThat(fetchedCat.getName()).isEqualTo(savedCat.getName());
    assertThat(fetchedCat.getDateOfBirth()).isEqualTo(savedCat.getDateOfBirth());
    assertThat(fetchedCat.getBreed()).isEqualTo(savedCat.getBreed());
    assertThat(fetchedCat.getRegistry()).isEqualTo(savedCat.getRegistry());
    assertThat(fetchedCat.getType()).isEqualTo(PetType.CAT);
  }

  @Test
  void shouldReturnSavedDogById() {
    Dog savedDog = petRepository.save(generateFakeDog());

    DogView fetchedDog = (DogView) petService.getPet(savedDog.getId());
    assertThat(fetchedDog.getId()).isEqualTo(savedDog.getId());
    assertThat(fetchedDog.getName()).isEqualTo(savedDog.getName());
    assertThat(fetchedDog.getDateOfBirth()).isEqualTo(savedDog.getDateOfBirth());
    assertThat(fetchedDog.getBreed()).isEqualTo(savedDog.getBreed());
    assertThat(fetchedDog.getSound()).isEqualTo(savedDog.getSound());
    assertThat(fetchedDog.getSize()).isEqualTo(savedDog.getSize());
    assertThat(fetchedDog.getCoatLength()).isEqualTo(savedDog.getCoatLength());
    assertThat(fetchedDog.getType()).isEqualTo(PetType.DOG);
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
    assertThat(catFromDb.getDateOfBirth()).isEqualTo(catTobeSaved.dateOfBirth());
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
    assertThat(dogFromDb.getDateOfBirth()).isEqualTo(dogTobeSaved.dateOfBirth());
    assertThat(dogFromDb.getBreed()).isEqualTo(dogTobeSaved.breed());
    assertThat(dogFromDb.getSound()).isEqualTo(dogTobeSaved.sound());
    assertThat(dogFromDb.getSize()).isEqualTo(dogTobeSaved.size());
    assertThat(dogFromDb.getCoatLength()).isEqualTo(dogTobeSaved.coatLength());
    assertThat(dogFromDb.getType()).isEqualTo(PetType.DOG);
  }

  @Test
  void shouldUpdateCat() {
    Cat savedCat = petRepository.save(generateFakeCat());
    PetDto catTobeUpdated = generateFakeCatDto();

    petService.updatePet(savedCat.getId(), catTobeUpdated);

    Optional<Pet> petOptional = petRepository.findById(savedCat.getId());
    assertThat(petOptional).isPresent();

    Cat catFromDb = (Cat) petOptional.get();
    assertThat(catFromDb.getName()).isEqualTo(catTobeUpdated.name());
    assertThat(catFromDb.getDateOfBirth()).isEqualTo(catTobeUpdated.dateOfBirth());
    assertThat(catFromDb.getBreed()).isEqualTo(catTobeUpdated.breed());
    assertThat(catFromDb.getRegistry()).isEqualTo(catTobeUpdated.registry());
    assertThat(catFromDb.getType()).isEqualTo(PetType.CAT);
  }

  @Test
  void shouldUpdateDog() {
    Dog savedDog = petRepository.save(generateFakeDog());
    PetDto dogTobeUpdated = generateFakeDogDto();

    petService.updatePet(savedDog.getId(), dogTobeUpdated);

    Optional<Pet> petOptional = petRepository.findById(savedDog.getId());
    assertThat(petOptional).isPresent();

    Dog dogFromDb = (Dog) petOptional.get();
    assertThat(dogFromDb.getName()).isEqualTo(dogTobeUpdated.name());
    assertThat(dogFromDb.getDateOfBirth()).isEqualTo(dogTobeUpdated.dateOfBirth());
    assertThat(dogFromDb.getBreed()).isEqualTo(dogTobeUpdated.breed());
    assertThat(dogFromDb.getSound()).isEqualTo(dogTobeUpdated.sound());
    assertThat(dogFromDb.getSize()).isEqualTo(dogTobeUpdated.size());
    assertThat(dogFromDb.getCoatLength()).isEqualTo(dogTobeUpdated.coatLength());
    assertThat(dogFromDb.getType()).isEqualTo(PetType.DOG);
  }

  @Test
  void shouldThrowPetNotFoundExceptionWhenUpdating() {
    long id = 99L;
    PetDto petTobeUpdated = generateFakeDogDto();
    assertThatThrownBy(() -> petService.updatePet(id, petTobeUpdated))
        .isInstanceOf(PetNotFoundException.class)
        .hasMessageContaining("Pet with id " + id + " not found");
  }

  @Test
  void shouldDeletePet() {
    Cat savedCat = petRepository.save(generateFakeCat());
    Dog savedDog = petRepository.save(generateFakeDog());

    petService.deletePet(savedCat.getId());
    assertThat(petRepository.findById(savedCat.getId())).isEmpty();

    petService.deletePet(savedDog.getId());
    assertThat(petRepository.findById(savedDog.getId())).isEmpty();
  }

  private PetDto generateFakeDogDto() {
    net.datafaker.providers.base.Dog fakeDog = faker.dog();
    return PetDto.aDog(
        fakeDog.name(),
        faker.date().birthdayLocalDate(1, 15),
        fakeDog.breed(),
        fakeDog.sound(),
        fakeDog.size(),
        fakeDog.coatLength());
  }

  private Dog generateFakeDog() {
    net.datafaker.providers.base.Dog fakeDog = faker.dog();
    return Dog.builder()
        .name(fakeDog.name())
        .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
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
        fakeCat.name(), faker.date().birthdayLocalDate(1, 15), fakeCat.breed(), fakeCat.registry());
  }

  private Cat generateFakeCat() {
    net.datafaker.providers.base.Cat fakeCat = faker.cat();
    return Cat.builder()
        .name(fakeCat.name())
        .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
        .breed(fakeCat.breed())
        .registry(fakeCat.registry())
        .type(PetType.CAT)
        .build();
  }
}
