package dev.chafon.datajpa.pet;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetRepository extends JpaRepository<Pet, Long> {
  @Query(
      """
            SELECT NEW dev.chafon.datajpa.pet.PetDto
            (
                p.id, p.name, p.age, p.breed, c.registry, d.sound, d.size, d.coatLength
            )
            FROM Pet p
            LEFT JOIN Cat c ON p.id = c.id
            LEFT JOIN Dog d ON p.id = d.id
            """)
  List<PetDto> findAllPets();

  @Query(
      """
            SELECT NEW dev.chafon.datajpa.pet.PetDto
            (
              p.id, p.name, p.age, p.breed, c.registry, d.sound, d.size, d.coatLength
            )
            FROM Pet p
            LEFT JOIN Cat c ON p.id = c.id
            LEFT JOIN Dog d ON p.id = d.id
            WHERE p.id = :id
            """)
  PetDto findPetById(Long id);
}
