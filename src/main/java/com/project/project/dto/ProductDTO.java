
package com.project.project.dto;

import jakarta.validation.constraints.Max;
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
public class ProductDTO {

	private int id;
	@NotBlank(message = "The name is required.")
	@Size(min = 3, max = 25, message = "The name must be from 3 to 25 characters.")
	private String name;

	@NotNull(message = "The quantity is required.")
	@Min(value = 1, message = "The quantity must be equal or greater than 1")
	@Max(value = 2147483647)
	private int quantity;

	@NotNull(message = "The price is required.")
	@Min(value = 1, message = "The price must be equal or greater than 1")
	@Max(value = 2147483647)
	private double price;

	//private byte[] fileData;

	public int getId() {
		return id;
	}

	/*
	 * public byte[] getFileData() { return fileData; }
	 * 
	 * public void setFileData(byte[] fileData) { this.fileData = fileData; }
	 */

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

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", quantity=" + quantity + ", price=" + price + "]";
	}


}