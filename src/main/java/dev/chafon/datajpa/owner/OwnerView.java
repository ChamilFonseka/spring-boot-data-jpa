package dev.chafon.datajpa.owner;

public interface OwnerView {
  Long getId();

  String getFirstName();

  String getLastName();

  String getPhoneNumber();

  AddressView getAddress();

  interface AddressView {
    String getStreet();

    String getCity();

    String getState();

    String getZipCode();
  }
}
