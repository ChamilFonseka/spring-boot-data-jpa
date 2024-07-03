package dev.chafon.datajpa.product;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private BigDecimal price;

  static Product of(ProductDto productDto) {
    return Product.builder().name(productDto.name()).price(productDto.price()).build();
  }

  void mapTo(ProductDto productDto) {
    this.name = productDto.name();
    this.price = productDto.price();
  }
}
