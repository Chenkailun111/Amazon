package com.jackchen.pojo;

import java.math.BigDecimal;

public class Product {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long stock;

    private Long majorId;

    private Long minorId;

    private String imgSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Long getMinorId() {
        return minorId;
    }

    public void setMinorId(Long minorId) {
        this.minorId = minorId;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", majorId=" + majorId +
                ", minorId=" + minorId +
                ", imgSource='" + imgSource + '\'' +
                '}';
    }
}