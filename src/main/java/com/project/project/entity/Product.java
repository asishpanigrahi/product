package com.project.project.entity;
import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE product SET deleted = 'true' WHERE id=?")
@Where(clause = "deleted=false")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 20)
	@NotBlank(message = "The name is required.")
	@Size(min = 3, max = 20, message = "The name must be from 3 to 20 characters.")
	private String name;

	@Column(nullable = false)
	@NotNull(message = "The quantity is required.")
	@Min(value = 1, message = "The quantity must be equal or greater than 1")
	private int quantity;

	@Column(nullable = false)
	@NotNull(message = "The price is required.")
	@Min(value = 1, message = "The price must be equal or greater than 1")
	private double price;

	@Column(nullable = false)
	private boolean deleted = false;


	//private byte[] fileData;

	 @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	    private List<FileEntity> files;
	 
	  public List<FileEntity> getFiles() {
		return files;
	}

	public void setFiles(List<FileEntity> files) {
		this.files = files;
	}

	@Override public String toString() { return "Product [id=" + id + ", name=" +
	  name + ", quantity=" + quantity + ", price=" + price + ", deleted=" + deleted
	  + "]"; }
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/*
	 * public byte[] getFileData() { return fileData; }
	 * 
	 * public void setFileData(byte[] fileData) { this.fileData = fileData; }
	 */
	 
	 
}
