package dev.chafon.datajpa.pet.dog;

import dev.chafon.datajpa.pet.PetView;

public interface DogView extends PetView {
  String getSound();

  String getSize();

  String getCoatLength();
}
