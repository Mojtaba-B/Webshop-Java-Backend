package de.mo.coding.webshop.repository;

import de.mo.coding.webshop.model.ProductCreateRequest;
import de.mo.coding.webshop.model.ProductResponse;

import java.util.*;
import java.util.stream.Collectors;

public class ProductRepository {

    private List<ProductResponse> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new ProductResponse(
                UUID.randomUUID().toString(),
                "AMD Ryzen 9 5950x",
                "this is a product",
                89400,
                Arrays.asList("AMD", "Processor")
        ));
        products.add(new ProductResponse(
                UUID.randomUUID().toString(),
                "Intel Core i9-9900KF",
                "this is a product",
                75400,
                Arrays.asList("Intel", "Processor")
        ));
        products.add(new ProductResponse(
                UUID.randomUUID().toString(),
                "NDIVIA GeForce GTX 1000 Ti Black Edition 11GB",
                "this is a product",
                49800,
                Arrays.asList("NDIVIA", "graphics")
        ));
    }

    public List<ProductResponse> findAll(String tag) {

        if (tag == null) return products;

        String finalTag = tag.toLowerCase();
        return products
                .stream()
                .filter(p -> lowerCaseTags(p).contains(finalTag))
                .collect(Collectors.toList());
    }


    private List<String> lowerCaseTags(ProductResponse p) {
        return p.getTags()
                .stream()
                .map(t -> t.toLowerCase())
                .collect(Collectors.toList());
    }


    public Optional<ProductResponse> findById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

    }

    public boolean delete(String id) {
        int beforeLength = products.size();
        products = products.stream()
                .filter(p -> !p.getId().equals(id))
                .collect(Collectors.toList());
        return beforeLength > products.size();
    }

    public ProductResponse save(ProductCreateRequest request) {
        ProductResponse response = new ProductResponse(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getDescription(),
                request.getPriceInCent(),
                request.getTags());

        products.add(response);
        return response;
    }
}
