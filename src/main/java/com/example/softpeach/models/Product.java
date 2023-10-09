package com.example.softpeach.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "weight")
    private int weight;
    @Column(name = "price")
    private int price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE)
    private Warehouse warehouse;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
    @JoinColumn(name = "image")
    private Image image;

    public void addImage(Image image) {
        image.setProduct(this);
        this.image=image;
    }

    public Long getImageId(){
        return image.getId();
    }
}
