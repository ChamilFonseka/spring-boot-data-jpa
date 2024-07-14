package dev.chafon.datajpa.vet;

import dev.chafon.datajpa.pet.PetType;
import java.util.Set;

public interface VetView {
  String getFirstName();

  String getLastName();

  String getPhoneNumber();

  Set<PetView> getPets();

  interface PetView {
    String getName();

    String getBreed();

    PetType getType();
  }
}
