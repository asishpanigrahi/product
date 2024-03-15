package com.project.project.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.project.project.dto.ProductDTO;
import com.project.project.entity.Product;

public interface ProductService {
	ProductDTO saveProduct(ProductDTO product);
	List<Product> getProducts();
	Product getProductById(int id);
	Product updateProduct(ProductDTO productDTO);
	List<Product> getProductsByName(String name);
	boolean softDeleteProduct(int id);
	ResponseEntity<String> uploadFile(MultipartFile file);
	

}