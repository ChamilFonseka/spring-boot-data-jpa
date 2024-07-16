package dev.chafon.datajpa.owner;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping
    public List<OwnerView> getOwners() {
        return ownerService.getOwners();
    }

    @GetMapping("/{id}")
    public OwnerView getOwner(@PathVariable Long id) {
        return ownerService.getOwner(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createOwner(@RequestBody OwnerDto ownerDto) {
        Long id = ownerService.createOwner(ownerDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOwner(@PathVariable Long id, @RequestBody OwnerDto ownerDto) {
        ownerService.updateOwner(id, ownerDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
    }

    @ExceptionHandler(OwnerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleOwnerNotFoundException(OwnerNotFoundException e) {
        return e.getMessage();
    }
}
