package dev.chafon.datajpa.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private CustomerAddress address;

    static Customer of(CustomerDto customerDto) {
        return Customer.builder()
                .name(customerDto.name())
                .address(CustomerAddress.builder()
                        .city(customerDto.city())
                        .state(customerDto.state())
                        .street(customerDto.street())
                        .zipCode(customerDto.zipCode())
                        .build())
                .build();
    }

    public void mapTo(CustomerDto customerDto) {
        this.name = customerDto.name();
        this.address.setCity(customerDto.city());
        this.address.setState(customerDto.state());
        this.address.setStreet(customerDto.street());
        this.address.setZipCode(customerDto.zipCode());
    }
}
