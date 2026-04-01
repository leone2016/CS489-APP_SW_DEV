package edu.miu.cs.cs489appsd.lab1.productmgmtapp.model;

import java.math.BigInteger;
import java.time.LocalDate;

public class Product {
    private BigInteger productId;
    private String name;
    private LocalDate dateSupplied;
    private Integer quantityInStock;
    private Double unitPrice;

    // Default constructor
    public Product() {
    }

    // Constructor with name and price (Partial)
    public Product(String name, Double unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    // Full constructor
    public Product(BigInteger productId, String name, LocalDate dateSupplied, Integer quantityInStock, Double unitPrice) {
        this.productId = productId;
        this.name = name;
        this.dateSupplied = dateSupplied;
        this.quantityInStock = quantityInStock;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateSupplied() {
        return dateSupplied;
    }

    public void setDateSupplied(LocalDate dateSupplied) {
        this.dateSupplied = dateSupplied;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", dateSupplied=" + dateSupplied +
                ", quantityInStock=" + quantityInStock +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
