package com.project.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.project.dto.ProductDTO;
import com.project.project.entity.Product;
import com.project.project.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
		private ProductRepository repository;
	Product p=new Product();
	
	public Product saveProduct(ProductDTO product) {
	p.setName(product.getName());
	p.setPrice(product.getPrice());
	p.setQuantity(product.getQuantity());
	return	repository.save(p);
	}
	
//	public List<Product> saveProducts(List<ProductDTO> products) {
//		p.setName(((ProductDTO) products).getName());
//		p.setPrice(((ProductDTO) products).getPrice());
//		p.setQuantity(((ProductDTO) products).getQuantity());
//		return repository.save(p);
//	}
	
	public List<Product> getProducts(){
		return repository.findAll();
	}
	
	public Product getProductById(int id) {
		return repository.findById(id).orElse(null);
	}
	
	public Product updateProduct(ProductDTO productDTO) {
		Product existingProduct=repository.findById(productDTO.getId()).orElse(null);
		existingProduct.setName(productDTO.getName());
		existingProduct.setPrice(productDTO.getPrice());
		existingProduct.setQuantity(productDTO.getQuantity());
		
		return repository.save(existingProduct);
	}

	
}
