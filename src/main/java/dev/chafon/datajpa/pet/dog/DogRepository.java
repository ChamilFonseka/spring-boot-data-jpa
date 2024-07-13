package dev.chafon.datajpa.pet.dog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
  DogView findDogViewById(Long id);
}
