package com.project.project.service;

import java.util.List;
import com.project.project.dto.ProductDTO;
import com.project.project.entity.Product;

public interface ProductService {
    Product saveProduct(ProductDTO product);
    List<Product> getProducts();
    Product getProductById(int id);
    Product updateProduct(ProductDTO productDTO);
    List<Product> getProductsByName(String name);
    boolean softDeleteProduct(int id);
  
}
