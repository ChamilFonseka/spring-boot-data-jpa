package dev.chafon.datajpa.vet;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/vets")
@RequiredArgsConstructor
public class VetController {

  private final VetService vetService;

  @GetMapping
  public List<VetView> getVets() {
    return vetService.getVets();
  }

  @GetMapping("/{id}")
  public VetView getVet(@PathVariable Long id) {
    return vetService.getVet(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Void> createVet(@RequestBody VetDto vetDto) {
    Long id = vetService.createVet(vetDto);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    return ResponseEntity.created(location).build();
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateVet(@PathVariable Long id, @RequestBody VetDto vetDto) {
    vetService.updateVet(id, vetDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteVet(@PathVariable Long id) {
    vetService.deleteVet(id);
  }

  @ExceptionHandler(VetNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleVetNotFoundException(VetNotFoundException e) {
    return e.getMessage();
  }
}
