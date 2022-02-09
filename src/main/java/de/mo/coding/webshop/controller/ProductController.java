package de.mo.coding.webshop.controller;

import de.mo.coding.webshop.model.ProductCreateRequest;
import de.mo.coding.webshop.model.ProductResponse;
import de.mo.coding.webshop.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    ProductRepository productRepository = new ProductRepository();

    @GetMapping("/products")
    public List<ProductResponse> getAllProducts(@RequestParam(required = false) String tag) {

        return productRepository.findAll(tag);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        Optional<ProductResponse> product = productRepository.findById(id);
        if (product.isPresent()) return ResponseEntity.ok(product.get());
        else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteById(@PathVariable String id) {
        if (!productRepository.delete(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products")
    public ProductResponse createProduct(@RequestBody ProductCreateRequest request) {
        return productRepository.save(request);
    }
}