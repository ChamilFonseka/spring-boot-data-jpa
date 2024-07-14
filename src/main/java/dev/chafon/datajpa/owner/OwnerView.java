package dev.chafon.datajpa.owner;

import dev.chafon.datajpa.pet.PetType;
import java.time.LocalDate;
import java.util.Set;

public interface OwnerView {
  Long getId();

  String getFirstName();

  String getLastName();

  String getPhoneNumber();

  AddressView getAddress();

  Set<PetView> getPets();

  interface AddressView {
    String getStreet();

    String getCity();

    String getState();

    String getZipCode();
  }

  interface PetView {
    Long getId();

    String getName();

    LocalDate getDateOfBirth();

    String getBreed();

    PetType getType();
  }
}
