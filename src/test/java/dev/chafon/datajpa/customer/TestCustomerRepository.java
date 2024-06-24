package dev.chafon.datajpa.customer;

interface TestCustomerRepository extends CustomerRepository {
    void deleteAll();
    void saveAll(Iterable<Customer> entities);
}
