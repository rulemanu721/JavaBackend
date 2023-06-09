package com.backend.Java.controller;

import com.backend.Java.entity.Product;
import com.backend.Java.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable int id) {
        Optional<Product> optionalProduct = productService.findById(id);

        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Product productDetails, @PathVariable int id) throws ParseException {
        Optional<Product> product = productService.findById(id);

        Date date = new Date();
        Date date1 = product.get().getLastUpdate();

        long differenceInMillis = date.getTime() - date1.getTime();
        long hours = (differenceInMillis / 3600000);

        if (!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (product.get().getStock() < 5 && hours < 24) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The product is locked");
        }

        product.get().setName(productDetails.getName());
        product.get().setDescription(productDetails.getDescription());
        product.get().setPrice(productDetails.getPrice());
        product.get().setStock(productDetails.getStock());

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product.get()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public List<Product> readAll() {
        List<Product> products = StreamSupport
                .stream(productService.findAll().spliterator(), false)
                .collect(Collectors.toList());

        return products;
    }

}
