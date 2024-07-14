package dev.chafon.datajpa.pet;

import dev.chafon.datajpa.owner.Owner;
import dev.chafon.datajpa.owner.OwnerNotFoundException;
import dev.chafon.datajpa.owner.OwnerRepository;
import dev.chafon.datajpa.pet.cat.Cat;
import dev.chafon.datajpa.pet.cat.CatRepository;
import dev.chafon.datajpa.pet.dog.Dog;
import dev.chafon.datajpa.pet.dog.DogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {

  private final PetRepository petRepository;
  private final CatRepository catRepository;
  private final DogRepository dogRepository;
  private final OwnerRepository ownerRepository;

  public List<PetView> getPets() {
    return petRepository.findPetViewBy();
  }

  public PetView getPet(Long id) {
    PetView petView =
        petRepository.findPetViewById(id).orElseThrow(() -> new PetNotFoundException(id));

    if (petView.getType().equals(PetType.CAT)) {
      return catRepository.findCatViewById(id);
    } else {
      return dogRepository.findDogViewById(id);
    }
  }

  public Long createPet(PetDto petDto) {
    Owner owner =
        ownerRepository
            .findById(petDto.ownerId())
            .orElseThrow(() -> new OwnerNotFoundException(petDto.ownerId()));

    Pet petToBeSaved;
    if (petDto.type().equals(PetType.CAT)) {
      petToBeSaved = Cat.of(petDto, owner);
    } else {
      petToBeSaved = Dog.of(petDto, owner);
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
