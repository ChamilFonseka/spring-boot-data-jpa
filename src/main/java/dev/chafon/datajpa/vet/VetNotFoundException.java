package dev.chafon.datajpa.vet;

public class VetNotFoundException extends RuntimeException {
    public VetNotFoundException(Long id) {
        super("Vet with id " + id + " not found");
    }
}
