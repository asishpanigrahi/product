package com.project.project.service;


	import java.util.List;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	import com.project.project.dto.ProductDTO;
	import com.project.project.entity.Product;

import com.project.project.repository.ProductRepository;
	import com.project.project.service.ProductService;

	@Service
	public class ProductServiceImpl implements ProductService {

	    @Autowired
	    private ProductRepository repository;
	   

	    @Override
	    public Product saveProduct(ProductDTO product) {
	        Product s = new Product();
	        s.setName(product.getName());
	        s.setPrice(product.getPrice());
	        s.setQuantity(product.getQuantity());
	        return repository.save(s);
	    }

	    @Override
	    public List<Product> getProducts() {
	        return repository.findAll();
	    }

	    @Override
	    public Product getProductById(int id) {
	        return repository.findById(id).orElse(null);
	    }

	    @Override
	    public Product updateProduct(ProductDTO productDTO) {
	        Product existingProduct = repository.findById(productDTO.getId()).orElse(null);
	        if (existingProduct != null) {
	            existingProduct.setName(productDTO.getName());
	            existingProduct.setPrice(productDTO.getPrice());
	            existingProduct.setQuantity(productDTO.getQuantity());
	            return repository.save(existingProduct);
	        } else {
	            return null;
	        }
	    }
	   
	}
	

