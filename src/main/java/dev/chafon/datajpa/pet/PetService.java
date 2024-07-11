package dev.chafon.datajpa.pet;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {

  private final PetRepository petRepository;

  public List<PetDto> getPets() {
    return petRepository.findAllPets();
  }

  public PetDto getPet(Long id) {
    PetDto petById = petRepository.findPetById(id);
    if (petById == null) {
      throw new PetNotFoundException(id);
    }
    return petById;
  }

  public Long createPet(PetDto petDto) {
    Pet petToBeSaved;
    if (petDto.type().equals(PetType.CAT)) {
      petToBeSaved = Cat.of(petDto);
    } else {
      petToBeSaved = Dog.of(petDto);
    }
    return petRepository.save(petToBeSaved).getId();
  }

  public void updatePet(Long id, PetDto petDto) {
    Pet pet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    pet.update(petDto);
    petRepository.save(pet);
  }

  public void deletePet(Long id) {
    petRepository.deleteById(id);
  }
}
