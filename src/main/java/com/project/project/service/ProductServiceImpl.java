package com.project.project.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import com.fasterxml.jackson.core.sym.Name;
//import com.project.project.dto.FileUploadDTO;
import com.project.project.dto.ProductDTO;
import com.project.project.entity.FileEntity;
import com.project.project.entity.Product;
import com.project.project.repository.FileRepository;
import com.project.project.repository.ProductRepository;

//import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	@Qualifier("modelMapper")
	private ModelMapper modelMapper;

	
	 @Override
	    public ProductDTO saveProduct(ProductDTO productDTO, List<MultipartFile> files) {
	        try {
	            Product product = modelMapper.map(productDTO, Product.class);
	            product = repository.save(product);

	            if (files != null && !files.isEmpty()) {
	                List<FileEntity> fileEntities = new ArrayList<>();
	                for (MultipartFile file : files) {
	                    FileEntity fileEntity = new FileEntity();
	                    fileEntity.setFilename(file.getOriginalFilename());
	                    fileEntity.setFileData(file.getBytes());
	                    fileEntity.setProduct(product); 
	                    fileEntities.add(fileEntity);
	                   
	                }
	               fileRepository.saveAll(fileEntities);
	               
	            }

	            return modelMapper.map(product, ProductDTO.class);
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to save product: " + e.getMessage(), e);
	        }
	    }

	@Override
	public List<ProductDTO> getProducts() {
		try {
			List<ProductDTO> productDTOList = new ArrayList<>();
			List<Product> products = repository.findAll();
			for (Product product : products) {
				ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
				productDTOList.add(productDTO);
			}
			return productDTOList;
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve products: " + e.getMessage(), e);
		}
	}

	@Override
	public ProductDTO getProductById(int id) {
		try {
			Optional<Product> optionalProduct = repository.findById(id);
			Product product = optionalProduct.orElse(null);
			if (product != null) {
				return modelMapper.map(product, ProductDTO.class);
			} else {
				return null;
			}
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
		existingProduct = this.modelMapper.map(productDTO, Product.class);
		try {
			return repository.save(existingProduct);
		} catch (Exception e) {
			throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
		}
	}

	@Override
	public List<ProductDTO> getProductsByName(String name) {
		try {
			List<Product> products = repository.findByName(name);
			List<ProductDTO> productDTOs = new ArrayList<>();
			for (Product product : products) {
				ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
				productDTOs.add(productDTO);
			}
			return productDTOs;
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve products by name: " + e.getMessage(), e);
		}
	}

	@Override
	public boolean softDeleteProduct(int id) {
		try {
			Optional<ProductDTO> optionalProductDTO = repository.findById(id)
					.map(product -> modelMapper.map(product, ProductDTO.class));
			if (optionalProductDTO.isPresent()) {
				repository.deleteById(id);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to soft delete product with id " + id + ": " + e.getMessage(), e);
		}
	}

	/*
	 * @Override public ResponseEntity<String> uploadFile(MultipartFile file) { try
	 * { return ResponseEntity.ok("File uploaded successfully: " +
	 * file.getOriginalFilename()); } catch (Exception e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body("Failed to upload file: " + e.getMessage()); }
	 * 
	 * }
	 */
@Override
	public boolean uploadFile(MultipartFile file) {
	    try {
	        if (file.isEmpty()) {
	            return false; // Indicates failure
	        }

	        byte[] fileData = file.getBytes();

	        FileEntity fileEntity = new FileEntity();
	        fileEntity.setFilename(file.getOriginalFilename());
	        fileEntity.setFileData(fileData);

	        repository.save(fileEntity);

	        return true; // Indicates success
	    } catch (IOException e) {
	        return false; // Indicates failure
	    }
	}

	@Override
    public boolean uploadFiles(List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    return false; // If any file is empty, return false
                }
                
                byte[] fileData = file.getBytes();
                
                FileEntity fileEntity = new FileEntity();
                fileEntity.setFilename(file.getOriginalFilename());
                fileEntity.setFileData(fileData);
                
                repository.save(fileEntity);
            }
            
            return true; // All files uploaded successfully
        } catch (IOException e) {
            return false; // Exception occurred during file upload
        }
    }

	/*
	 * public Optional getFile(int id) { return fileRepository.findById(id);
	 * 
	 * }
	 */

	@Override
	public List<FileEntity> getAllFiles() {
		return fileRepository.findAll();
	}
	
	@Override
    public FileEntity getFileById(int id) {
        return fileRepository.findById(id).orElse(null);
    }
}
