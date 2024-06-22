package dev.chafon.datajpa.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer c join fetch c.address")
    List<Customer> findAllWithAddresses();
}
