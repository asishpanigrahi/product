package com.project.project.service;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.sym.Name;
import com.project.project.dto.ProductDTO;
import com.project.project.entity.Product;
import com.project.project.repository.ProductRepository;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	@Qualifier("modelMapper")
	private ModelMapper modelMapper;

	@Override
	public ProductDTO saveProduct(ProductDTO productDTO) {
		
		/*
		 * if (product == null) { throw new
		 * IllegalArgumentException("ProductDTO cannot be null"); } Product newProduct =
		 * new Product(); newProduct.setName(product.getName());
		 * newProduct.setPrice(product.getPrice());
		 * newProduct.setQuantity(product.getQuantity());
		 */
		Product product = this.modelMapper.map(productDTO, Product.class);
		try {
			product = repository.save(product);
			return this.modelMapper.map(product, ProductDTO.class);
		} catch (Exception e) {
			throw new RuntimeException("Failed to save product: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Product> getProducts() {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve products: " + e.getMessage(), e);
		}
	}

	@Override
	public Product getProductById(int id) {
		try {
			Optional<Product> optionalProduct = repository.findById(id);
			return optionalProduct.orElse(null);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve product with id " + id + ": " + e.getMessage(), e);
		}
	}

	@Override
	public Product updateProduct(ProductDTO productDTO) {
		if (productDTO == null) {
			throw new IllegalArgumentException("ProductDTO cannot be null");
		}
		Product existingProduct = repository.findById(productDTO.getId()).orElse(null);
		if (existingProduct == null) {
			throw new IllegalArgumentException("Product not found with id: " + productDTO.getId());
		}
		/*
		 * existingProduct.setName(productDTO.getName());
		 * existingProduct.setPrice(productDTO.getPrice());
		 * existingProduct.setQuantity(productDTO.getQuantity());
		 */
		existingProduct = this.modelMapper.map(productDTO, Product.class);
		try {
			return repository.save(existingProduct);
		} catch (Exception e) {
			throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Product> getProductsByName(String name) {
		try {
			return repository.findByName(name);
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve products by name: " + e.getMessage(), e);
		}
	}

	@Override
	public boolean softDeleteProduct(int id) {
		try {
			Optional<Product> optionalProduct = repository.findById(id);
			if (optionalProduct.isPresent()) {
				repository.deleteById(id);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to soft delete product with id " + id + ": " + e.getMessage(), e);
		}
	}
	
	@Override
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
           return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }
	
}
	

