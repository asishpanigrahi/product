// ProductController.java
package com.project.project.controller;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.project.dto.ProductDTO;
import com.project.project.entity.Product;
import com.project.project.service.ProductService;

import ch.qos.logback.classic.Logger;



@RequestMapping("/product")
@RestController
public class ProductController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            logger.info("Adding new product: {}", productDTO);
            Product product = service.saveProduct(productDTO);
            logger.info("Product added: {}", product);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            logger.error("Error adding product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAllProduct() {
        try {
            logger.info("Fetching all products");
            List<Product> products = service.getProducts();
            logger.info("Products fetched: {}", products);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error fetching products: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        try {
            logger.info("Fetching product by id: {}", id);
            Product product = service.getProductById(id);
            if (product != null) {
                logger.info("Product fetched: {}", product);
                return ResponseEntity.ok(product);
            } else {
                logger.warn("Product with id {} not found", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error fetching product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDTO productDTO) {
        try {
            logger.info("Updating product: {}", productDTO);
            Product updatedProduct = service.updateProduct(productDTO);
            if (updatedProduct != null) {
                logger.info("Product updated: {}", updatedProduct);
                return ResponseEntity.ok(updatedProduct);
            } else {
                logger.warn("Product with id {} not found", productDTO.getId());
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error updating product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}
