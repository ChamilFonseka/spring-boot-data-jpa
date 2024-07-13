package dev.chafon.datajpa.owner;

import jakarta.persistence.Embeddable;

@Embeddable
public record Address(String street, String city, String state, String zipCode) {}
