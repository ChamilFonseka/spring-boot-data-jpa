package dev.chafon.datajpa.pet.cat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Long> {
  CatView findCatViewById(Long id);
}
