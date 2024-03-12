// ProductController.java
package com.project.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.project.dto.ProductDTO;
import com.project.project.entity.Product;
import com.project.project.service.ProductService;

@RestController("/")
public class ProductController {
    
    @Autowired
    private ProductService service;
    
    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody ProductDTO product) {
        Product p=  service.saveProduct(product);
        return p;
    }
    
//    @PostMapping("/addAllProduct")
//    public List<ProductDTO> addAllProduct(@RequestBody  List<ProductDTO> productDTOs) {
//    	Product p=  (Product) service.saveProducts(productDTOs);
//        return (List<ProductDTO>) p;
//    }
    
    @GetMapping("/getProduct")
    public List<Product> findAllProduct(){
        return service.getProducts();
    }
    
    @GetMapping("/getProduct/{id}")
    public Product getById(int id) {
        return service.getProductById(id);
    }
    
    @PutMapping("/update")
    public Product updateProduct(@RequestBody ProductDTO productDTO) {
        return service.updateProduct(productDTO);
    }
}
