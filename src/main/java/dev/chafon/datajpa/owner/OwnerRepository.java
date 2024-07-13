package dev.chafon.datajpa.owner;

import dev.chafon.datajpa.BaseRepository;
import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends BaseRepository<Owner, Long> {
  List<OwnerView> findOwnerViewBy();

  Optional<OwnerView> findOwnerViewById(Long id);
}
