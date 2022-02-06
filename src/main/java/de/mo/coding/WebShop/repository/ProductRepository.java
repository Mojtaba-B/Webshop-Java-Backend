package de.mo.coding.WebShop.repository;

import de.mo.coding.WebShop.model.ProductResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepository {

    private final List<ProductResponse> products =
            Arrays.asList(
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
    ;

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
}
