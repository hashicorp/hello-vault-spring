package com.hashicorp.hellovaultspring.database;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsTable extends CrudRepository<Product, Long> {
    List<Product> findAll();
}
