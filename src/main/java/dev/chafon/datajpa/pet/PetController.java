package dev.chafon.datajpa.pet;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
public class PetController {

  private final PetService petService;

  @GetMapping
  List<PetDto> getPets() {
    return petService.getPets();
  }

  @GetMapping("/{id}")
  PetDto getPet(@PathVariable Long id) {
    return petService.getPet(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<Void> createPet(@RequestBody PetDto petDto) {
    Long id = petService.createPet(petDto);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void updatePet(@PathVariable Long id, @RequestBody PetDto petDto) {
    petService.updatePet(id, petDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deletePet(@PathVariable Long id) {
    petService.deletePet(id);
  }

  @ExceptionHandler(PetNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handlePetNotFoundException(PetNotFoundException e) {
    return e.getMessage();
  }
}
