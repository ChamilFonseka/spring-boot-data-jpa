package dev.chafon.datajpa.pet;

import static dev.chafon.datajpa.TestUtil.*;
import static org.assertj.core.api.Assertions.*;

import dev.chafon.datajpa.TestContainersConfiguration;
import dev.chafon.datajpa.TestUtil;
import dev.chafon.datajpa.owner.Owner;
import dev.chafon.datajpa.owner.OwnerNotFoundException;
import dev.chafon.datajpa.owner.OwnerRepository;
import dev.chafon.datajpa.pet.cat.Cat;
import dev.chafon.datajpa.pet.cat.CatView;
import dev.chafon.datajpa.pet.dog.Dog;
import dev.chafon.datajpa.pet.dog.DogView;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import({TestContainersConfiguration.class, PetService.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PetServiceTest {

    @Autowired
    private PetService petService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private static Owner owner;

    @BeforeAll
    void beforeAll() {
        owner = ownerRepository.save(TestUtil.generateFakeOwner());
    }

    @AfterAll
    void afterAll() {
        ownerRepository.deleteById(owner.getId());
    }

    @Test
    void shouldReturnAllPets() {
        Cat savedCat = petRepository.save(generateFakeCat(owner, Set.of()));
        Dog savedDog = petRepository.save(generateFakeDog(owner, Set.of()));

        List<PetView> allPets = petService.getPets();
        assertThat(allPets).isNotNull();
        assertThat(allPets).hasSize(2);

        assertThat(allPets).extracting(PetView::getId).contains(savedCat.getId(), savedDog.getId());

        assertThat(allPets).extracting(PetView::getName).contains(savedCat.getName(), savedDog.getName());

        assertThat(allPets)
                .extracting(PetView::getDateOfBirth)
                .contains(savedCat.getDateOfBirth(), savedDog.getDateOfBirth());

        assertThat(allPets).extracting(PetView::getBreed).contains(savedCat.getBreed(), savedDog.getBreed());

        assertThat(allPets).extracting(PetView::getType).contains(PetType.CAT, PetType.DOG);

        assertThat(allPets)
                .extracting(PetView::getOwner)
                .extracting(PetView.OwnerView::getId)
                .contains(owner.getId());

        assertThat(allPets)
                .extracting(PetView::getOwner)
                .extracting(PetView.OwnerView::getFirstName)
                .contains(owner.getFirstName());

        assertThat(allPets)
                .extracting(PetView::getOwner)
                .extracting(PetView.OwnerView::getLastName)
                .contains(owner.getLastName());

        assertThat(allPets)
                .extracting(PetView::getOwner)
                .extracting(PetView.OwnerView::getPhoneNumber)
                .contains(owner.getPhoneNumber());
    }

    @Test
    void shouldReturnCatById() {
        Cat savedCat = petRepository.save(generateFakeCat(owner, Set.of()));

        CatView fetchedCat = (CatView) petService.getPet(savedCat.getId());
        assertThat(fetchedCat.getId()).isEqualTo(savedCat.getId());
        assertThat(fetchedCat.getName()).isEqualTo(savedCat.getName());
        assertThat(fetchedCat.getDateOfBirth()).isEqualTo(savedCat.getDateOfBirth());
        assertThat(fetchedCat.getBreed()).isEqualTo(savedCat.getBreed());
        assertThat(fetchedCat.getRegistry()).isEqualTo(savedCat.getRegistry());
        assertThat(fetchedCat.getType()).isEqualTo(PetType.CAT);
    }

    @Test
    void shouldReturnDogById() {
        Dog savedDog = petRepository.save(generateFakeDog(owner, Set.of()));

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
    void shouldThrowOwnerNotFoundException() {
        long id = 99L;
        PetDto catTobeSaved = generateFakeCatDto(id);
        assertThatThrownBy(() -> petService.createPet(catTobeSaved))
                .isInstanceOf(OwnerNotFoundException.class)
                .hasMessageContaining("Owner with id " + id + " not found");
    }

    @Test
    void shouldSaveACat() {
        PetDto catTobeSaved = generateFakeCatDto(owner.getId());

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
        PetDto dogTobeSaved = generateFakeDogDto(owner.getId());

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
        Cat savedCat = petRepository.save(generateFakeCat(owner, Set.of()));
        PetDto catTobeUpdated = generateFakeCatDto(owner.getId());

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
        Dog savedDog = petRepository.save(generateFakeDog(owner, Set.of()));
        PetDto dogTobeUpdated = generateFakeDogDto(owner.getId());

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
    void shouldThrowPetNotFoundExceptionOnUpdate() {
        long id = 99L;
        PetDto petTobeUpdated = generateFakeDogDto(owner.getId());
        assertThatThrownBy(() -> petService.updatePet(id, petTobeUpdated))
                .isInstanceOf(PetNotFoundException.class)
                .hasMessageContaining("Pet with id " + id + " not found");
    }

    @Test
    void shouldDeletePet() {
        Cat savedCat = petRepository.save(generateFakeCat(owner, Set.of()));
        Dog savedDog = petRepository.save(generateFakeDog(owner, Set.of()));

        petService.deletePet(savedCat.getId());
        assertThat(petRepository.findById(savedCat.getId())).isEmpty();

        petService.deletePet(savedDog.getId());
        assertThat(petRepository.findById(savedDog.getId())).isEmpty();
    }
}
