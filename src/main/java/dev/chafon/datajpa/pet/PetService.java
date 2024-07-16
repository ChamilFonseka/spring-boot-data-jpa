package dev.chafon.datajpa.pet;

import dev.chafon.datajpa.owner.Owner;
import dev.chafon.datajpa.owner.OwnerNotFoundException;
import dev.chafon.datajpa.owner.OwnerRepository;
import dev.chafon.datajpa.pet.cat.Cat;
import dev.chafon.datajpa.pet.cat.CatRepository;
import dev.chafon.datajpa.pet.dog.Dog;
import dev.chafon.datajpa.pet.dog.DogRepository;
import dev.chafon.datajpa.vet.Vet;
import dev.chafon.datajpa.vet.VetNotFoundException;
import dev.chafon.datajpa.vet.VetRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final CatRepository catRepository;
    private final DogRepository dogRepository;
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;

    public List<PetView> getPets() {
        return petRepository.findPetViewBy();
    }

    public PetView getPet(Long id) {
        PetView petView = petRepository.findPetViewById(id).orElseThrow(() -> new PetNotFoundException(id));

        if (petView.getType().equals(PetType.CAT)) {
            return catRepository.findCatViewById(id);
        } else {
            return dogRepository.findDogViewById(id);
        }
    }

    public Long createPet(PetDto petDto) {
        Owner owner = ownerRepository
                .findById(petDto.ownerId())
                .orElseThrow(() -> new OwnerNotFoundException(petDto.ownerId()));

        Set<Vet> vets = new HashSet<>();
        if (petDto.vetIds() != null) {
            petDto.vetIds().forEach(vetId -> {
                Vet vet = vetRepository.findById(vetId).orElseThrow(() -> new VetNotFoundException(vetId));
                vets.add(vet);
            });
        }

        Pet petToBeSaved;
        if (petDto.type().equals(PetType.CAT)) {
            petToBeSaved = Cat.of(petDto, owner, vets);
        } else {
            petToBeSaved = Dog.of(petDto, owner, vets);
        }
        return petRepository.save(petToBeSaved).getId();
    }

    public void updatePet(Long id, PetDto petDto) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        Owner owner = ownerRepository
                .findById(petDto.ownerId())
                .orElseThrow(() -> new OwnerNotFoundException(petDto.ownerId()));

        Set<Vet> vets;
        if (petDto.vetIds() != null) {
            vets = new HashSet<>();
            petDto.vetIds().forEach(vetId -> {
                Vet vet = vetRepository.findById(vetId).orElseThrow(() -> new VetNotFoundException(vetId));
                vets.add(vet);
            });
        } else {
            vets = pet.getVets();
        }

        pet.update(petDto, owner, vets);
        petRepository.save(pet);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
