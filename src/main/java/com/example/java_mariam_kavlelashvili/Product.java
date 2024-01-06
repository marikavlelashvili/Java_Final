package com.example.java_mariam_kavlelashvili;

import java.util.Date;
import java.util.Objects;

public class Product {
    private String name;
    private int quantity;
    private String Owner;

    public Product() {

    }

    public Product(String name, int quantity, String owner) {
        this.name = name;
        this.quantity = quantity;
        Owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", Owner='" + Owner + '\'' +
                '}';
    }
}
