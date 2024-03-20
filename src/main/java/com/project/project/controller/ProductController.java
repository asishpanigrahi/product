// ProductController.java
package com.project.project.controller;

import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.project.dto.ProductDTO;
import com.project.project.entity.FileEntity;
import com.project.project.entity.Product;
import com.project.project.service.ProductService;
import org.springframework.ui.Model;

import ch.qos.logback.classic.Logger;
//import ch.qos.logback.core.model.Model;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/product")
@RestController
public class ProductController {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService service;
	
	@PostMapping
	public ResponseEntity<ProductDTO> addProduct(@ModelAttribute ProductDTO productDTO,
	                                             @RequestParam(value = "files", required = false) List<MultipartFile> files) {
	    try {
	        logger.info("Adding new product");

	        ProductDTO savedProduct = service.saveProduct(productDTO, files);

	        logger.info("Product added");

	        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	    } catch (Exception e) {
	        logger.error("Error adding product: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }
	}


	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAllProduct() {
		try {
			logger.info("Fetching all products");
			List<ProductDTO> productDTOs = service.getProducts();
			logger.info("Products fetched: {}", productDTOs.size());
			return ResponseEntity.ok(productDTOs);
		} catch (Exception e) {
			logger.error("Error fetching products: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getById(@PathVariable int id) {
		try {
			logger.info("Fetching product by id: ", id);
			ProductDTO productDTO = service.getProductById(id);
			if (productDTO != null) {
				logger.info("Product fetched: ", productDTO);
				return ResponseEntity.ok(productDTO);
			} else {
				logger.warn("Product with id {} not found", id);
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			logger.error("Error fetching product: ", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductDTO productDTO) {
		try {
			logger.info("Updating product:");
			Product updatedProduct = service.updateProduct(productDTO);
			if (updatedProduct != null) {
				logger.info("Product updated:");
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

	@GetMapping("/name/{name}")
	public ResponseEntity<List<ProductDTO>> getProductsByName(@PathVariable String name) {
		try {
			List<ProductDTO> productDTOs = service.getProductsByName(name);
			if (!productDTOs.isEmpty()) {
				return ResponseEntity.ok(productDTOs);
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

	/*
	 * private static String UPLOADED_FOLDER =
	 * "C:\\Users\\AsishPanigrahi\\Downloads";
	 * 
	 * @PostMapping("/upload") public String singleFileUpload(@RequestParam("file")
	 * MultipartFile file, RedirectAttributes redirectAttributes) { if
	 * (file.isEmpty()) { redirectAttributes.addFlashAttribute("message",
	 * "Please select a file to upload"); return "redirect:uploadStatus"; } try {
	 * byte[] bytes = file.getBytes(); Path path = Paths.get(UPLOADED_FOLDER +
	 * file.getOriginalFilename()); Files.write(path, bytes);
	 * redirectAttributes.addFlashAttribute("message",
	 * "Your file successfully uploaded '" + file.getOriginalFilename() + "'"); }
	 * catch (IOException e) { e.printStackTrace(); } return "upload"; }
	 */
	@PostMapping("/upload")
	public ResponseEntity<String> singleFileUpload(@RequestParam("files") MultipartFile file, RedirectAttributes redirectAttributes) {
	    try {
	        logger.info("Uploading file: {}", file.getOriginalFilename());
	        
	        boolean result = service.uploadFile(file);

	        if (result) {
	            logger.info("File uploaded successfully: {}", file.getOriginalFilename());
	            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
	        } else {
	            logger.error("Failed to upload file or no file selected.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload file or no file selected.");
	        }
	    } catch (Exception e) {
	        logger.error("Failed to upload file: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
	    }
	}

	@PostMapping("/upload/multiples")
	public ResponseEntity<String> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files,
	                                                   RedirectAttributes redirectAttributes) {
	    try {
	        logger.info("Uploading {} files", files.size());
	        boolean result = service.uploadFiles(files);

	        if (result) {
	            logger.info("Multiple files uploaded successfully");
	            return ResponseEntity.ok("Multiple files uploaded successfully");
	        } else {
	            logger.error("Failed to upload files or one or more files are empty.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload files or one or more files are empty.");
	        }
	    } catch (Exception e) {
	        logger.error("Failed to upload files: {}", e.getMessage(), e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files: " + e.getMessage());
	    }
	}

	/*
	 * @GetMapping("/downloadFile/{fileName:.+}") public ResponseEntity < Resource >
	 * downloadFile(@PathVariable String fileName, HttpServletRequest request) { //
	 * Load file as Resource DatabaseFile databaseFile = service.getFile(fileName);
	 * 
	 * return ResponseEntity.ok()
	 * .contentType(MediaType.parseMediaType(databaseFile.getFileType()))
	 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
	 * databaseFile.getFileName() + "\"") .body(new
	 * ByteArrayResource(databaseFile.getData())); }
	 */

	@GetMapping("/files")
	@ResponseBody
	public ResponseEntity<List<FileEntity>> getAllFiles() {
		try {

			return ResponseEntity.ok(service.getAllFiles());
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFileById(@PathVariable("id") int id) {
		try {
			FileEntity file = service.getFileById(id);
			if (file != null) {
				return ResponseEntity.ok()
						.header("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"")
						.body(file.getFileData());
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}