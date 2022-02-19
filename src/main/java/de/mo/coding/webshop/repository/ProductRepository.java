package de.mo.coding.webshop.repository;

import de.mo.coding.webshop.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,String> { }


