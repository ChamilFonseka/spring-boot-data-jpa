package dev.chafon.datajpa.pet.cat;

import dev.chafon.datajpa.BaseRepository;

public interface CatRepository extends BaseRepository<Cat, Long> {
  CatView findCatViewById(Long id);
}
