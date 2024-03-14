package com.project.project.entity;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false, length = 20)
    @NotBlank(message = "The name is required.")
    @Size(min = 3,max = 20, message = "The name must be from 3 to 20 characters.")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "The quantity is required.")
    @Min(value = 1, message = "The quantity must be equal or greater than 1")
    private int quantity;
    
    @Column(nullable = false)
    @NotNull(message = "The price is required.")
    @Min(value = 1, message = "The price must be equal or greater than 1")
    private double price;

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

	public void setDeleted(boolean b) {
		// TODO Auto-generated method stub
		
	}

	    
    
}