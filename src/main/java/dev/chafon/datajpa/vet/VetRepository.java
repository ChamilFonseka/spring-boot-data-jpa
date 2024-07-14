package dev.chafon.datajpa.vet;

import dev.chafon.datajpa.BaseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;

public interface VetRepository extends BaseRepository<Vet, Long> {
  @EntityGraph(attributePaths = {"pets"})
  List<VetView> findVetViewBy();

  @EntityGraph(attributePaths = {"pets"})
  Optional<VetView> findVetViewById(Long id);
}
