package dev.chafon.datajpa.customer;

import dev.chafon.datajpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends BaseRepository<Customer, Integer> {
    @Query("select c from Customer c join fetch c.address")
    List<Customer> findAllWithAddresses();
}
