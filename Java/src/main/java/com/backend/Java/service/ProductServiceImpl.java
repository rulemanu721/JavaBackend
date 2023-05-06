package com.backend.Java.service;

import com.backend.Java.entity.Product;
import com.backend.Java.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    @Transactional(readOnly = true)
    public Iterable<Product> findAll() {

        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        productRepository.deleteById(id);

    }
}
