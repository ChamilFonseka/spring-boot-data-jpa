package dev.chafon.datajpa.owner;

public record OwnerDto(
        String firstName,
        String lastName,
        String phoneNumber,
        String street,
        String city,
        String state,
        String zipCode) {}
