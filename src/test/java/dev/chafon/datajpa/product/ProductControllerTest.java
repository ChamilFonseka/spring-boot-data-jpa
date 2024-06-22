package dev.chafon.datajpa.product;

import dev.chafon.datajpa.TestcontainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    private static final String BASE_URL = "/api/v1/products";

    private final Product iphone = Product.builder()
            .name("iPhone 15 Pro")
            .price(BigDecimal.valueOf(999))
            .build();

    private final Product samsung = Product.builder()
            .name("Samsung Galaxy S22")
            .price(BigDecimal.valueOf(799.50))
            .build();

    private final ProductDto productToCreate = new ProductDto(
            "Google Pixel 8",
            BigDecimal.valueOf(700.00));

    private final ProductDto productToUpdate = new ProductDto(
            "iPhone 15",
            BigDecimal.valueOf(750.00));

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void shouldGetAllProducts() {
        productRepository.save(iphone);
        productRepository.save(samsung);

        ResponseEntity<Product[]> response = restTemplate
                .getForEntity(BASE_URL, Product[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Product[] products = response.getBody();

        assertThat(products).hasSize(2);
        assertThat(products[0].getId()).isEqualTo(iphone.getId());
        assertThat(products[0].getName()).isEqualTo(iphone.getName());
        assertThat(products[0].getPrice()).isEqualByComparingTo(iphone.getPrice());
        assertThat(products[1].getId()).isEqualTo(samsung.getId());
        assertThat(products[1].getName()).isEqualTo(samsung.getName());
        assertThat(products[1].getPrice()).isEqualByComparingTo(samsung.getPrice());
    }

    @Test
    void shouldGetProduct() {
        productRepository.save(iphone);

        ResponseEntity<Product> response = restTemplate
                .getForEntity(BASE_URL + "/{id}", Product.class, iphone.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Product product = response.getBody();

        assertThat(product.getId()).isEqualTo(iphone.getId());
        assertThat(product.getName()).isEqualTo(iphone.getName());
        assertThat(product.getPrice()).isEqualByComparingTo(iphone.getPrice());
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() {
        Integer productId = 999;

        ResponseEntity<Product> response = restTemplate
                .getForEntity(BASE_URL + "/{id}", Product.class, productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateProduct() {
        ResponseEntity<Product> response = restTemplate.postForEntity(BASE_URL, productToCreate, Product.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = response.getHeaders().getLocation();
        Product productCreated = restTemplate.getForObject(location, Product.class);
        assertThat(productCreated.getId()).isNotNull();
        assertThat(productCreated.getName()).isEqualTo(productToCreate.name());
        assertThat(productCreated.getPrice()).isEqualByComparingTo(productToCreate.price());
    }

    @Test
    void shouldUpdateProduct() {
        productRepository.save(iphone);

        ResponseEntity<Void> response = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.PUT,
                new HttpEntity<>(productToUpdate), Void.class, iphone.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        productRepository.findById(iphone.getId())
                .ifPresentOrElse(
                        product -> {
                            assertThat(product.getName()).isEqualTo(productToUpdate.name());
                            assertThat(product.getPrice()).isEqualByComparingTo(productToUpdate.price());
                        },
                        () -> fail("Product not found")
                );
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingProductDoesNotExist() {
        ResponseEntity<Void> response = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.PUT,
                new HttpEntity<>(productToUpdate), Void.class, 999);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    void shouldDeleteProduct() {
        productRepository.save(iphone);

        ResponseEntity<Void> response = restTemplate.exchange(BASE_URL + "/{id}", HttpMethod.DELETE,
                null, Void.class, iphone.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(productRepository.findById(iphone.getId())).isEmpty();
    }
}
