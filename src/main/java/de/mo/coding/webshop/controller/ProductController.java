package de.mo.coding.webshop.controller;

import de.mo.coding.webshop.entity.ProductEntity;
import de.mo.coding.webshop.exceptions.IdNotFoundException;
import de.mo.coding.webshop.model.ProductCreateRequest;
import de.mo.coding.webshop.model.ProductResponse;
import de.mo.coding.webshop.model.ProductUpdateRequest;
import de.mo.coding.webshop.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<ProductResponse> getAllProducts(@RequestParam(required = false) String tag) {

        return productRepository.findAll()
                .stream()
                .filter((productEntity) -> tag == null || productEntity.getTags().contains(tag))
                .map((productEntity) -> mapToResponse(productEntity))
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public ProductResponse getProductById(@PathVariable String id) {
        ProductEntity product = productRepository.getOne(id);
        return mapToResponse(product);
    }

    @PostMapping("/products")
    public ProductResponse createProduct(@RequestBody ProductCreateRequest request) {
        ProductEntity productEntity = new ProductEntity(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getDescription(),
                request.getPriceInCent(),
                request.getTags());

        ProductEntity savedProduct = productRepository.save(productEntity);

        return mapToResponse(savedProduct);
    }

    @NotNull
    private ProductResponse mapToResponse(ProductEntity savedProduct) {
        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPriceInCent(),
                savedProduct.getTags());
    }

    @PutMapping("/products/{id}")
    public ProductResponse updateProduct(
            @PathVariable String id,
            @RequestBody ProductUpdateRequest request
    ) {

        final ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException
                        ("Product with id " + id + " not found",
                                HttpStatus.BAD_REQUEST
                        )
                );


        final ProductEntity updateProduct = new ProductEntity(
                product.getId(),
                (request.getName() != null) ? request.getName() : product.getName(),
                (request.getDescription() != null) ? request.getDescription() : product.getDescription(),
                (request.getPriceInCent() != null) ? request.getPriceInCent() : product.getPriceInCent(),
                product.getTags()
        );

        ProductEntity savedProduct = productRepository.save(updateProduct);
        return mapToResponse(savedProduct);

    }


}