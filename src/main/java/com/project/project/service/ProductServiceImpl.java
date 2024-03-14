package com.project.project.service;
	import java.util.List;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	import com.project.project.dto.ProductDTO;
	import com.project.project.entity.Product;
	import com.project.project.repository.ProductRepository;
	import java.util.Optional;

	@Service
	public class ProductServiceImpl implements ProductService {

	    @Autowired
	    private ProductRepository repository;

	    @Override
	    public Product saveProduct(ProductDTO product) {
	        if (product == null) {
	            throw new IllegalArgumentException("ProductDTO cannot be null");
	        }


	        Product newProduct = new Product();
	        newProduct.setName(product.getName());
	        newProduct.setPrice(product.getPrice());
	        newProduct.setQuantity(product.getQuantity());
	        
	        try {
	            return repository.save(newProduct);
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

	        existingProduct.setName(productDTO.getName());
	        existingProduct.setPrice(productDTO.getPrice());
	        existingProduct.setQuantity(productDTO.getQuantity());
	        
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
	                Product product = optionalProduct.get();
	                product.setDeleted(true); 
	                repository.save(product);
	                return true;
	            } else {
	                return false;
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to soft delete product with id " + id + ": " + e.getMessage(), e);
	        }
	    }
	   
	}
	

