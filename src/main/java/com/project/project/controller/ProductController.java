// ProductController.java
package com.project.project.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.project.dto.ProductDTO;
import com.project.project.entity.Product;
import com.project.project.service.ProductService;

import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;



@RequestMapping("/product")
@RestController
public class ProductController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            logger.info("Adding new product: {}");
            ProductDTO product = service.saveProduct(productDTO);
            logger.info("Product added: ");
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            logger.error("Error adding product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAllProduct() {
        try {
            logger.info("Fetching all products");
            List<Product> products = service.getProducts();
            logger.info("Products fetched: {}");
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error fetching products: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        try {
            logger.info("Fetching product by id: {}");
            Product product = service.getProductById(id);
            if (product != null) {
                logger.info("Product fetched: {}");
                return ResponseEntity.ok(product);
            } else {
                logger.warn("Product with id {} not found", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {

            logger.error("Error fetching product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductDTO productDTO) {
        try {
            logger.info("Updating product: {}");
            Product updatedProduct = service.updateProduct(productDTO);
            if (updatedProduct != null) {
                logger.info("Product updated: {}");
                return ResponseEntity.ok(updatedProduct);
            } else {
                logger.warn("Product with id {} not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error updating product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/name{name}") 
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name) {
        try {
            List<Product> products = service.getProductsByName(name);
            if (!products.isEmpty()) {
                return ResponseEntity.ok(products);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

 

        @DeleteMapping("/{id}")
        public ResponseEntity<String> softDeleteProduct(@PathVariable int id) {
            try {
                boolean deleted = service.softDeleteProduct(id);
                if (deleted) {
                    return ResponseEntity.ok("Product soft deleted successfully");
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
            }
        }
        
        private static String UPLOADED_FOLDER = "C:\\Users\\AsishPanigrahi\\Downloads";

        @GetMapping("/")
        public String index() {
            return "upload";
        }

        @PostMapping("/upload") 
        public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes) {

            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
                return "redirect:uploadStatus";
            }

            try {

              
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);

                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" + file.getOriginalFilename() + "'");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "upload";
        }

        @GetMapping("/uploadStatus")
        public String uploadStatus() {
            return "uploadStatus";
        }

      
}