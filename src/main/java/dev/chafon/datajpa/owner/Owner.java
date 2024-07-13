package dev.chafon.datajpa.owner;

import dev.chafon.datajpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "owners")
@DynamicUpdate
public class Owner extends BaseEntity {
  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  @Embedded
  private Address address;

  public static Owner of(OwnerDto ownerDto) {
    return Owner.builder()
        .firstName(ownerDto.firstName())
        .lastName(ownerDto.lastName())
        .phoneNumber(ownerDto.phoneNumber())
        .address(
            new Address(ownerDto.street(), ownerDto.city(), ownerDto.state(), ownerDto.zipCode()))
        .build();
  }

  public void update(OwnerDto ownerDto) {
    this.firstName = ownerDto.firstName();
    this.lastName = ownerDto.lastName();
    this.phoneNumber = ownerDto.phoneNumber();
    this.address =
        new Address(ownerDto.street(), ownerDto.city(), ownerDto.state(), ownerDto.zipCode());
  }

  @Override
  public String toString() {
    return "Owner{"
        + "id='"
        + getId()
        + '\''
        + "firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", phoneNumber='"
        + phoneNumber
        + '\''
        + ", address="
        + address
        + '}';
  }
}
