package de.mo.coding.WebShop.repository;

import de.mo.coding.WebShop.model.ProductResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductRepository {

    public List<ProductResponse> findAll(String tag) {
        List<ProductResponse> products = Arrays.asList(
                new ProductResponse(
                        "1",
                        "AMD Ryzen 9 5950x",
                        "this is a product",
                        89400,
                        Arrays.asList("AMD", "Processor")
                ),
                new ProductResponse(
                        "2",
                        "Intel Core i9-9900KF",
                        "this is a product",
                        75400,
                        Arrays.asList("Intel", "Processor")
                ),
                new ProductResponse(
                        "3",
                        "NDIVIA GeForce GTX 1000 Ti Black Edition 11GB",
                        "this is a product",
                        49800,
                        Arrays.asList("NDIVIA", "graphics")
                )
        );
        if (tag == null) return products;
        tag = tag.toLowerCase();
        List<ProductResponse> filtered = new ArrayList<>();

        for (ProductResponse p : products) {
            List<String> lowercaseTags = new ArrayList<>();
            for (String t : p.getTags()) {
                lowercaseTags.add(t.toLowerCase());
            }
            if (lowercaseTags.contains(tag)) {
                filtered.add(p);
            }
        }
        return filtered;
    }
}
