package dev.chafon.datajpa.product;

interface TestProductRepository extends ProductRepository {
    void deleteAll();
}
