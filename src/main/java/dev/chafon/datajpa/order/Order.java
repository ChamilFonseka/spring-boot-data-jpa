package dev.chafon.datajpa.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.chafon.datajpa.customer.Customer;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String orderNumber;
  private LocalDateTime orderCreatedTime;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @JsonBackReference
  private Customer customer;
}
