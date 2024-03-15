package com.project.project.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.project.entity.Product;
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByName(String name);

}	
