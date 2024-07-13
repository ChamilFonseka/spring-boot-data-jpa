package dev.chafon.datajpa.pet;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
  List<PetView> findPetViewBy();

  Optional<PetView> findPetViewById(Long id);
}
