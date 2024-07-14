package dev.chafon.datajpa.pet;

import java.time.LocalDate;

public interface PetView {
  Long getId();

  String getName();

  LocalDate getDateOfBirth();

  String getBreed();

  PetType getType();

  OwnerView getOwner();

  interface OwnerView {
    Long getId();

    String getFirstName();

    String getLastName();

    String getPhoneNumber();
  }
}
