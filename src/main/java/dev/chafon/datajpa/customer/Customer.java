package dev.chafon.datajpa.customer;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private CustomerAddress address;

  // @OneToMany(mappedBy = "customer")
  // @ToString.Exclude
  // @EqualsAndHashCode.Exclude
  // @JsonManagedReference
  // private Set<Order> orders;

  public static Customer of(CustomerDto customerDto) {
    CustomerAddress address =
        CustomerAddress.builder()
            .street(customerDto.street())
            .city(customerDto.city())
            .state(customerDto.state())
            .zipCode(customerDto.zipCode())
            .build();

    Customer customer = Customer.builder().name(customerDto.name()).address(address).build();

    address.setCustomer(customer);

    return customer;
  }

  public static void map(Customer customer, CustomerDto customerDto) {
    customer.setName(customerDto.name());

    if (customer.getAddress() != null) {
      customer.getAddress().setStreet(customerDto.street());
      customer.getAddress().setCity(customerDto.city());
      customer.getAddress().setState(customerDto.state());
      customer.getAddress().setZipCode(customerDto.zipCode());
    } else if (customerDto.street() != null
        || customerDto.city() != null
        || customerDto.state() != null
        || customerDto.zipCode() != null) {

      CustomerAddress address =
          CustomerAddress.builder()
              .street(customerDto.street())
              .city(customerDto.city())
              .state(customerDto.state())
              .zipCode(customerDto.zipCode())
              .customer(customer)
              .build();

      customer.setAddress(address);
    }
  }
}
