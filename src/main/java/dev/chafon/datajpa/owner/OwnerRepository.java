package dev.chafon.datajpa.owner;

import dev.chafon.datajpa.BaseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;

public interface OwnerRepository extends BaseRepository<Owner, Long> {
    @EntityGraph(attributePaths = {"pets"})
    List<OwnerView> findOwnerViewBy();

    @EntityGraph(attributePaths = {"pets"})
    Optional<OwnerView> findOwnerViewById(Long id);
}
