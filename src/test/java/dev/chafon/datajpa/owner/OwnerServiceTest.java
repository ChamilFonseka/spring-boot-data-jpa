package dev.chafon.datajpa.owner;

import static dev.chafon.datajpa.TestUtil.generateFakeOwner;
import static dev.chafon.datajpa.TestUtil.generateFakeOwnerDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.chafon.datajpa.TestContainersConfiguration;
import java.util.List;
import java.util.Optional;
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

  @AfterEach
  void tearDown() {
    ownerRepository.deleteAll();
  }

  @Test
  void shouldReturnAllOwners() {
    Owner owner1 = ownerRepository.save(generateFakeOwner());
    Owner owner2 = ownerRepository.save(generateFakeOwner());

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
  }

  @Test
  void shouldReturnOwnerById() {
    Owner owner = ownerRepository.save(generateFakeOwner());

    OwnerView fetchedOwner = ownerService.getOwner(owner.getId());
    assertThat(fetchedOwner.getId()).isEqualTo(owner.getId());
    assertThat(fetchedOwner.getFirstName()).isEqualTo(owner.getFirstName());
    assertThat(fetchedOwner.getLastName()).isEqualTo(owner.getLastName());
    assertThat(fetchedOwner.getPhoneNumber()).isEqualTo(owner.getPhoneNumber());
    assertThat(fetchedOwner.getAddress().getStreet()).isEqualTo(owner.getAddress().street());
    assertThat(fetchedOwner.getAddress().getCity()).isEqualTo(owner.getAddress().city());
    assertThat(fetchedOwner.getAddress().getState()).isEqualTo(owner.getAddress().state());
    assertThat(fetchedOwner.getAddress().getZipCode()).isEqualTo(owner.getAddress().zipCode());
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
