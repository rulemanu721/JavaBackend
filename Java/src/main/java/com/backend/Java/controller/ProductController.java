package com.backend.Java.controller;

import com.backend.Java.entity.Product;
import com.backend.Java.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> create (@RequestBody Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read (@PathVariable int id){
        Optional<Product> optionalProduct = productService.findById(id);

        if(!optionalProduct.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@RequestBody Product productDetails, @PathVariable  int id){
        Optional<Product> product = productService.findById(id);

        if(!product.isPresent()){
            return ResponseEntity.notFound().build();
        }

        product.get().setName(productDetails.getName());
        product.get().setDescription(productDetails.getDescription());
        product.get().setPrice(productDetails.getPrice());
        product.get().setStock(productDetails.getStock());

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product.get()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable int id){
        //Check put
        if(!productService.findById(id).isPresent()){
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //Streams

    @GetMapping
    public List<Product> readAll(){
        List<Product> products = StreamSupport
                .stream(productService.findAll().spliterator(),false)
                .collect(Collectors.toList());

        return products;
    }



}
