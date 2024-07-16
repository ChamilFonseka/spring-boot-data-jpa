package dev.chafon.datajpa.pet.dog;

import dev.chafon.datajpa.BaseRepository;

public interface DogRepository extends BaseRepository<Dog, Long> {
    DogView findDogViewById(Long id);
}
