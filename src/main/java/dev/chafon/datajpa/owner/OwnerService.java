package dev.chafon.datajpa.owner;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public List<OwnerView> getOwners() {
        return ownerRepository.findOwnerViewBy();
    }

    public OwnerView getOwner(Long id) {
        return ownerRepository.findOwnerViewById(id).orElseThrow(() -> new OwnerNotFoundException(id));
    }

    @Transactional
    public Long createOwner(OwnerDto ownerDto) {
        return ownerRepository.save(Owner.of(ownerDto)).getId();
    }

    @Transactional
    public void updateOwner(Long id, OwnerDto ownerDto) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new OwnerNotFoundException(id));
        owner.update(ownerDto);
        ownerRepository.save(owner);
    }

    @Transactional
    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}
