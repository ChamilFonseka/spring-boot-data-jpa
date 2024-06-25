package dev.chafon.datajpa.customer;

import dev.chafon.datajpa.order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private CustomerAddress address;
    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> orders;

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

    void mapTo(CustomerDto customerDto) {
        this.name = customerDto.name();
        this.address.setCity(customerDto.city());
        this.address.setState(customerDto.state());
        this.address.setStreet(customerDto.street());
        this.address.setZipCode(customerDto.zipCode());
    }
}
