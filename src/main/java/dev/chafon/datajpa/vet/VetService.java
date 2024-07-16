package dev.chafon.datajpa.vet;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VetService {

    private final VetRepository vetRepository;

    public List<VetView> getVets() {
        return vetRepository.findVetViewBy();
    }

    public VetView getVet(Long id) {
        return vetRepository.findVetViewById(id).orElseThrow(() -> new VetNotFoundException(id));
    }

    @Transactional
    public Long createVet(VetDto vetDto) {
        return vetRepository.save(Vet.of(vetDto)).getId();
    }

    @Transactional
    public void updateVet(Long id, VetDto vetDto) {
        Vet vet = vetRepository.findById(id).orElseThrow(() -> new VetNotFoundException(id));
        vet.update(vetDto);
        vetRepository.save(vet);
    }

    @Transactional
    public void deleteVet(Long id) {
        vetRepository.deleteById(id);
    }
}
