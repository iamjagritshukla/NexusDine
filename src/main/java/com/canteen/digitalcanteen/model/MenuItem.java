package com.canteen.digitalcanteen.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 100) private String name;
    @Column(nullable = false, length = 60) private String category;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal price;
    @Column(length = 500) private String description;
    @Column(length = 500) private String imageUrl;
    @Column(nullable = false) private boolean available = true;
    @Column(nullable = false, columnDefinition = "integer default 100") private int stockQuantity = 100;
    @Column(nullable = false, columnDefinition = "integer default 10") private int lowStockThreshold = 10;
    @Column(nullable = false, precision = 5, scale = 2, columnDefinition = "decimal(5,2) default 0.00") private BigDecimal discountPercent = BigDecimal.ZERO;

    public MenuItem() {}
    public MenuItem(String name, String category, BigDecimal price, String description, String imageUrl, boolean available) {
        this.name=name; this.category=category; this.price=price; this.description=description; this.imageUrl=imageUrl; this.available=available;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getCategory(){return category;} public void setCategory(String category){this.category=category;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal price){this.price=price;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}
    public boolean isAvailable(){return available;} public void setAvailable(boolean available){this.available=available;}
    public int getStockQuantity(){return stockQuantity;} public void setStockQuantity(int stockQuantity){this.stockQuantity=Math.max(0, stockQuantity);}
    public int getLowStockThreshold(){return lowStockThreshold;} public void setLowStockThreshold(int lowStockThreshold){this.lowStockThreshold=Math.max(0, lowStockThreshold);}
    public BigDecimal getDiscountPercent(){return discountPercent;} public void setDiscountPercent(BigDecimal discountPercent){this.discountPercent=discountPercent==null?BigDecimal.ZERO:discountPercent.max(BigDecimal.ZERO).min(new BigDecimal("100"));}
    public BigDecimal getEffectivePrice(){return price.subtract(price.multiply(getDiscountPercent()).divide(new BigDecimal("100"), 2, java.math.RoundingMode.HALF_UP));}
    public boolean isLowStock(){return stockQuantity<=lowStockThreshold;}
}
