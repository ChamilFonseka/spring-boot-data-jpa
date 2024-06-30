package dev.chafon.datajpa.customer;

public record CustomerDto(
        Integer id,
        String name,
        String street,
        String city,
        String state,
        String zipCode) {
}
