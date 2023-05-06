package com.backend.Java.service;

import com.backend.Java.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    public Iterable<Product> findAll();

    public Iterable<Product> findAll(Pageable pageable);

    public Optional<Product> findById(int id);

    public Product save(Product product);

    public void deleteById(int id);
}
