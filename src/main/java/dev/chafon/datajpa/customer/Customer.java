package dev.chafon.datajpa.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.chafon.datajpa.order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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
//    @OneToMany(mappedBy = "customer")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @JsonManagedReference
//    private Set<Order> orders;
}
