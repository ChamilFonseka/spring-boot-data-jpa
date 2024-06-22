package dev.chafon.datajpa.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class ProductService {

    private final ProductRepository productRepository;

    List<Product> getProducts() {
        return productRepository.findAll();
    }
    Product getProduct(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
    Product createProduct(ProductDto productDto) {
        return productRepository.save(Product.of(productDto));
    }

    void updateProduct(Integer id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.mapTo(productDto);
        productRepository.save(product);
    }

    void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
