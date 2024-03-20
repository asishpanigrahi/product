package com.project.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.project.project.dto.ProductDTO;
import com.project.project.entity.FileEntity;
import com.project.project.entity.Product;

public interface ProductService {
	 ProductDTO saveProduct(ProductDTO productDTO, List<MultipartFile> files);

	List<ProductDTO> getProducts();

	ProductDTO getProductById(int id);

	Product updateProduct(ProductDTO productDTO);

	List<ProductDTO> getProductsByName(String name);

	boolean softDeleteProduct(int id);

	boolean uploadFile(MultipartFile file);

	boolean uploadFiles(List<MultipartFile> files);
	

	// public Optional<FileEntity> getFile(String fileName);
	List<FileEntity> getAllFiles();

	FileEntity getFileById(int id);

}