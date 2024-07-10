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
    return petRepository.findPetById(id);
  }

  public Long createPet(PetType petType, PetDto petDto) {
    Pet petToBeSaved;
    if (petType == PetType.CAT) {
      petToBeSaved = Cat.of(petDto);
    } else {
      petToBeSaved = Dog.of(petDto);
    }
    return petRepository.save(petToBeSaved).getId();
  }
}
