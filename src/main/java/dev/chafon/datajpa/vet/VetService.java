package dev.chafon.datajpa.vet;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VetService {

    private final VetRepository vetRepository;

    public List<VetView> getVets() {
        return vetRepository.findVetViewBy();
    }

    public VetView getVet(Long id) {
        return vetRepository.findVetViewById(id).orElseThrow(() -> new VetNotFoundException(id));
    }

    public Long createVet(VetDto vetDto) {
        return vetRepository.save(Vet.of(vetDto)).getId();
    }

    public void updateVet(Long id, VetDto vetDto) {
        Vet vet = vetRepository.findById(id).orElseThrow(() -> new VetNotFoundException(id));
        vet.update(vetDto);
        vetRepository.save(vet);
    }

    public void deleteVet(Long id) {
        vetRepository.deleteById(id);
    }
}
