package dev.chafon.datajpa.owner;

import static dev.chafon.datajpa.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.chafon.datajpa.TestContainersConfiguration;
import dev.chafon.datajpa.pet.Pet;
import dev.chafon.datajpa.pet.TestPetRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import({TestContainersConfiguration.class, OwnerService.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class OwnerServiceTest {

  @Autowired private OwnerService ownerService;

  @Autowired private TestOwnerRepository ownerRepository;

  @Autowired private TestPetRepository petRepository;

  @Autowired EntityManager entityManager;

  @AfterEach
  void tearDown() {
    ownerRepository.deleteAll();
  }

  @Test
  void shouldReturnAllOwners() {
    Owner owner1 = ownerRepository.save(generateFakeOwner());
    Pet owner1Cat = petRepository.save(generateFakeCat(owner1));
    Pet owner1Dog = petRepository.save(generateFakeDog(owner1));

    Owner owner2 = ownerRepository.save(generateFakeOwner());
    Pet owner2Cat = petRepository.save(generateFakeCat(owner2));
    Pet owner2Dog = petRepository.save(generateFakeDog(owner2));

    entityManager.clear();

    List<OwnerView> allOwners = ownerService.getOwners();
    assertThat(allOwners).isNotNull();
    assertThat(allOwners).hasSize(2);

    assertThat(allOwners).extracting(OwnerView::getId).contains(owner1.getId(), owner2.getId());

    assertThat(allOwners)
        .extracting(OwnerView::getFirstName)
        .contains(owner1.getFirstName(), owner2.getFirstName());

    assertThat(allOwners)
        .extracting(OwnerView::getLastName)
        .contains(owner1.getLastName(), owner2.getLastName());

    assertThat(allOwners)
        .extracting(OwnerView::getPhoneNumber)
        .contains(owner1.getPhoneNumber(), owner2.getPhoneNumber());

    assertThat(allOwners)
        .extracting(OwnerView::getAddress)
        .extracting(OwnerView.AddressView::getStreet)
        .contains(owner1.getAddress().street(), owner2.getAddress().street());

    assertThat(allOwners)
        .extracting(OwnerView::getAddress)
        .extracting(OwnerView.AddressView::getCity)
        .contains(owner1.getAddress().city(), owner2.getAddress().city());

    assertThat(allOwners)
        .extracting(OwnerView::getAddress)
        .extracting(OwnerView.AddressView::getState)
        .contains(owner1.getAddress().state(), owner2.getAddress().state());

    assertThat(allOwners)
        .extracting(OwnerView::getAddress)
        .extracting(OwnerView.AddressView::getZipCode)
        .contains(owner1.getAddress().zipCode(), owner2.getAddress().zipCode());

    assertThat(allOwners).extracting(OwnerView::getPets).extracting(Set::size).contains(2, 2);

    assertThat(allOwners)
        .extracting(OwnerView::getPets)
        .flatExtracting(pets -> pets)
        .extracting(OwnerView.PetView::getName)
        .contains(
            owner1Cat.getName(), owner1Dog.getName(), owner2Cat.getName(), owner2Dog.getName());

    assertThat(allOwners)
        .extracting(OwnerView::getPets)
        .flatExtracting(pets -> pets)
        .extracting(OwnerView.PetView::getDateOfBirth)
        .contains(
            owner1Cat.getDateOfBirth(),
            owner1Dog.getDateOfBirth(),
            owner2Cat.getDateOfBirth(),
            owner2Dog.getDateOfBirth());

    assertThat(allOwners)
        .extracting(OwnerView::getPets)
        .flatExtracting(pets -> pets)
        .extracting(OwnerView.PetView::getBreed)
        .contains(
            owner1Cat.getBreed(), owner1Dog.getBreed(), owner2Cat.getBreed(), owner2Dog.getBreed());

    assertThat(allOwners)
        .extracting(OwnerView::getPets)
        .flatExtracting(pets -> pets)
        .extracting(OwnerView.PetView::getType)
        .contains(
            owner1Cat.getType(), owner1Dog.getType(), owner2Cat.getType(), owner2Dog.getType());
  }

  @Test
  void shouldReturnOwnerById() {
    Owner owner = ownerRepository.save(generateFakeOwner());
    Pet cat = petRepository.save(generateFakeCat(owner));
    Pet dog = petRepository.save(generateFakeDog(owner));

    entityManager.clear();

    OwnerView fetchedOwner = ownerService.getOwner(owner.getId());
    assertThat(fetchedOwner.getId()).isEqualTo(owner.getId());
    assertThat(fetchedOwner.getFirstName()).isEqualTo(owner.getFirstName());
    assertThat(fetchedOwner.getLastName()).isEqualTo(owner.getLastName());
    assertThat(fetchedOwner.getPhoneNumber()).isEqualTo(owner.getPhoneNumber());
    assertThat(fetchedOwner.getAddress().getStreet()).isEqualTo(owner.getAddress().street());
    assertThat(fetchedOwner.getAddress().getCity()).isEqualTo(owner.getAddress().city());
    assertThat(fetchedOwner.getAddress().getState()).isEqualTo(owner.getAddress().state());
    assertThat(fetchedOwner.getAddress().getZipCode()).isEqualTo(owner.getAddress().zipCode());
    assertThat(fetchedOwner.getPets()).hasSize(2);
    assertThat(fetchedOwner.getPets())
        .extracting(OwnerView.PetView::getName)
        .contains(cat.getName(), dog.getName());
  }

  @Test
  void shouldThrowOwnerNotFoundException() {
    long id = 99L;
    assertThatThrownBy(() -> ownerService.getOwner(id))
        .isInstanceOf(OwnerNotFoundException.class)
        .hasMessageContaining("Owner with id " + id + " not found");
  }

  @Test
  void shouldCreateOwner() {
    OwnerDto ownerDto = generateFakeOwnerDto();
    Long id = ownerService.createOwner(ownerDto);

    Optional<Owner> optionalOwner = ownerRepository.findById(id);
    assertThat(optionalOwner).isPresent();

    Owner owner = optionalOwner.get();
    assertThat(owner.getFirstName()).isEqualTo(ownerDto.firstName());
    assertThat(owner.getLastName()).isEqualTo(ownerDto.lastName());
    assertThat(owner.getPhoneNumber()).isEqualTo(ownerDto.phoneNumber());
    assertThat(owner.getAddress().street()).isEqualTo(ownerDto.street());
    assertThat(owner.getAddress().city()).isEqualTo(ownerDto.city());
    assertThat(owner.getAddress().state()).isEqualTo(ownerDto.state());
    assertThat(owner.getAddress().zipCode()).isEqualTo(ownerDto.zipCode());
  }

  @Test
  void shouldUpdateOwner() {
    Owner owner = ownerRepository.save(generateFakeOwner());
    OwnerDto ownerDto = generateFakeOwnerDto();

    ownerService.updateOwner(owner.getId(), ownerDto);

    Optional<Owner> optionalOwner = ownerRepository.findById(owner.getId());
    assertThat(optionalOwner).isPresent();

    Owner updatedOwner = optionalOwner.get();
    assertThat(updatedOwner.getFirstName()).isEqualTo(ownerDto.firstName());
    assertThat(updatedOwner.getLastName()).isEqualTo(ownerDto.lastName());
    assertThat(updatedOwner.getPhoneNumber()).isEqualTo(ownerDto.phoneNumber());
    assertThat(updatedOwner.getAddress().street()).isEqualTo(ownerDto.street());
    assertThat(updatedOwner.getAddress().city()).isEqualTo(ownerDto.city());
    assertThat(updatedOwner.getAddress().state()).isEqualTo(ownerDto.state());
    assertThat(updatedOwner.getAddress().zipCode()).isEqualTo(ownerDto.zipCode());
  }

  @Test
  void shouldThrowOwnerNotFoundExceptionOnUpdate() {
    long id = 99L;
    OwnerDto ownerDto = generateFakeOwnerDto();
    assertThatThrownBy(() -> ownerService.updateOwner(id, ownerDto))
        .isInstanceOf(OwnerNotFoundException.class)
        .hasMessageContaining("Owner with id " + id + " not found");
  }

  @Test
  void shouldDeleteOwner() {
    Owner owner = ownerRepository.save(generateFakeOwner());

    ownerService.deleteOwner(owner.getId());
    Optional<Owner> optionalOwner = ownerRepository.findById(owner.getId());
    assertThat(optionalOwner).isEmpty();
  }
}
