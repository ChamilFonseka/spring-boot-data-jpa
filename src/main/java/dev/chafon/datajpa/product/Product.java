package dev.chafon.datajpa.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
        return Product.builder()
                .name(productDto.name())
                .price(productDto.price())
                .build();
    }

    void mapTo(ProductDto productDto) {
        this.name = productDto.name();
        this.price = productDto.price();
    }
}
