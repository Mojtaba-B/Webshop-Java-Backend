package de.mo.coding.webshop.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    private String id;
    private String name;
    private String description;
    private Integer priceInCent;

    @ElementCollection
    private List<String> tags;

    public ProductEntity() {
    }

    public ProductEntity(String id,
                         String name,
                         String description,
                         Integer priceInCent,
                         List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priceInCent = priceInCent;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPriceInCent() {
        return priceInCent;
    }

    public List<String> getTags() {
        return tags;
    }
}
