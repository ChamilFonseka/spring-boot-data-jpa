package dev.chafon.datajpa.vet;

import static dev.chafon.datajpa.TestUtil.*;
import static dev.chafon.datajpa.TestUtil.generateFakeOwner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.chafon.datajpa.TestContainersConfiguration;
import dev.chafon.datajpa.TestUtil;
import dev.chafon.datajpa.owner.Owner;
import dev.chafon.datajpa.owner.OwnerRepository;
import dev.chafon.datajpa.pet.Pet;
import dev.chafon.datajpa.pet.PetRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import({TestContainersConfiguration.class, VetService.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class VetServiceTest {

  @Autowired private VetService vetService;

  @Autowired private VetRepository vetRepository;

  @Autowired private OwnerRepository ownerRepository;

  @Autowired private PetRepository petRepository;

  @Autowired EntityManager entityManager;

  @Test
  void shouldReturnAllVets() {
    Vet vet1 = vetRepository.save(TestUtil.generateFakeVet());
    Vet vet2 = vetRepository.save(TestUtil.generateFakeVet());

    Owner owner1 = ownerRepository.save(generateFakeOwner());
    Pet owner1Cat = petRepository.save(generateFakeCat(owner1, Set.of(vet1, vet2)));
    Pet owner1Dog = petRepository.save(generateFakeDog(owner1, Set.of(vet1, vet2)));

    Owner owner2 = ownerRepository.save(generateFakeOwner());
    Pet owner2Cat = petRepository.save(generateFakeCat(owner2, Set.of(vet1, vet2)));
    Pet owner2Dog = petRepository.save(generateFakeDog(owner2, Set.of(vet1, vet2)));

    entityManager.flush();
    entityManager.clear();

    List<VetView> allVets = vetService.getVets();
    assertThat(allVets).isNotNull();
    assertThat(allVets).hasSize(2);

    assertThat(allVets).extracting(VetView::getId).contains(vet1.getId(), vet2.getId());

    assertThat(allVets)
        .extracting(VetView::getFirstName)
        .contains(vet1.getFirstName(), vet2.getFirstName());

    assertThat(allVets)
        .extracting(VetView::getLastName)
        .contains(vet1.getLastName(), vet2.getLastName());

    assertThat(allVets)
        .extracting(VetView::getPhoneNumber)
        .contains(vet1.getPhoneNumber(), vet2.getPhoneNumber());

    assertThat(allVets)
        .extracting(VetView::getPets)
        .flatExtracting(pet -> pet)
        .extracting(VetView.PetView::getName)
        .contains(
            owner1Cat.getName(), owner1Dog.getName(), owner2Cat.getName(), owner2Dog.getName());

    assertThat(allVets)
        .extracting(VetView::getPets)
        .flatExtracting(pet -> pet)
        .extracting(VetView.PetView::getBreed)
        .contains(
            owner1Cat.getBreed(), owner1Dog.getBreed(), owner2Cat.getBreed(), owner2Dog.getBreed());

    assertThat(allVets)
        .extracting(VetView::getPets)
        .flatExtracting(pet -> pet)
        .extracting(VetView.PetView::getType)
        .contains(
            owner1Cat.getType(), owner1Dog.getType(), owner2Cat.getType(), owner2Dog.getType());
  }

  @Test
  void shouldReturnVetById() {
    Vet vet = vetRepository.save(TestUtil.generateFakeVet());

    Owner owner = ownerRepository.save(generateFakeOwner());
    ownerRepository.save(owner);

    Pet cat = petRepository.save(generateFakeCat(owner, Set.of(vet)));
    Pet dog = petRepository.save(generateFakeDog(owner, Set.of(vet)));

    entityManager.flush();
    entityManager.clear();

    VetView fetchedVet = vetService.getVet(vet.getId());
    assertThat(fetchedVet.getId()).isEqualTo(vet.getId());
    assertThat(fetchedVet.getFirstName()).isEqualTo(vet.getFirstName());
    assertThat(fetchedVet.getLastName()).isEqualTo(vet.getLastName());
    assertThat(fetchedVet.getPhoneNumber()).isEqualTo(vet.getPhoneNumber());

    assertThat(fetchedVet.getPets()).hasSize(2);

    assertThat(fetchedVet.getPets())
        .extracting(VetView.PetView::getName)
        .contains(cat.getName(), dog.getName());

    assertThat(fetchedVet.getPets())
        .extracting(VetView.PetView::getBreed)
        .contains(cat.getBreed(), dog.getBreed());

    assertThat(fetchedVet.getPets())
        .extracting(VetView.PetView::getType)
        .contains(cat.getType(), dog.getType());
  }

  @Test
  void shouldThrowVetNotFoundException() {
    Long id = 99L;
    assertThatThrownBy(() -> vetService.getVet(id))
        .isInstanceOf(VetNotFoundException.class)
        .hasMessageContaining("Vet with id " + id + " not found");
  }

  @Test
  void shouldCreateVet() {
    VetDto vetDto = generateFakeVetDto();
    Long id = vetService.createVet(vetDto);

    Optional<Vet> optionalVet = vetRepository.findById(id);
    assertThat(optionalVet).isPresent();

    Vet vet = optionalVet.get();
    assertThat(vet.getId()).isEqualTo(id);
    assertThat(vet.getFirstName()).isEqualTo(vetDto.firstName());
    assertThat(vet.getLastName()).isEqualTo(vetDto.lastName());
    assertThat(vet.getPhoneNumber()).isEqualTo(vetDto.phoneNumber());
  }

  @Test
  void shouldUpdateVet() {
    Vet vet = vetRepository.save(TestUtil.generateFakeVet());
    VetDto vetDto = generateFakeVetDto();

    vetService.updateVet(vet.getId(), vetDto);

    Optional<Vet> optionalVet = vetRepository.findById(vet.getId());
    assertThat(optionalVet).isPresent();

    Vet updatedVet = optionalVet.get();
    assertThat(updatedVet.getFirstName()).isEqualTo(vetDto.firstName());
    assertThat(updatedVet.getLastName()).isEqualTo(vetDto.lastName());
    assertThat(updatedVet.getPhoneNumber()).isEqualTo(vetDto.phoneNumber());
  }

  @Test
  void shouldThrowVetNotFoundExceptionOnUpdate() {
    long id = 99L;
    VetDto vetDto = generateFakeVetDto();
    assertThatThrownBy(() -> vetService.updateVet(id, vetDto))
        .isInstanceOf(VetNotFoundException.class)
        .hasMessageContaining("Vet with id " + id + " not found");
  }

  @Test
  void shouldDeleteVet() {
    Vet vet = vetRepository.save(TestUtil.generateFakeVet());
    vetRepository.save(vet);

    vetService.deleteVet(vet.getId());

    Optional<Vet> optionalVet = vetRepository.findById(vet.getId());
    assertThat(optionalVet).isEmpty();
  }
}
