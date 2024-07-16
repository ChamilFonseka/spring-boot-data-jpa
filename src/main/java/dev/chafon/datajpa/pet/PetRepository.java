package dev.chafon.datajpa.pet;

import dev.chafon.datajpa.BaseRepository;
import java.util.List;
import java.util.Optional;

public interface PetRepository extends BaseRepository<Pet, Long> {
    List<PetView> findPetViewBy();

    Optional<PetView> findPetViewById(Long id);
}
