package com.mstudio.entity;

import java.util.HashMap;
import java.util.Map;

public class Product {
    String name;
    String code;
    Double price;
    Map<Integer, Double> packagingPrice;


    public Product() {
    }

    public Product(String name, String code, Double price) {
        this.name = name;
        this.code = code;
        this.price = price;
        packagingPrice = new HashMap<>();
    }

    public void displayBundlePrice(){
        for (Map.Entry<Integer, Double> set : packagingPrice.entrySet()) {

            // Printing all elements of a Map
            System.out.println(set.getKey() + " for $"
                    + set.getValue());
        }
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<Integer, Double> getPackagingPrice() {
        return packagingPrice;
    }

    public void setPackagingPrice(Map<Integer, Double> packagingPrice) {
        this.packagingPrice = packagingPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", packagingPrice=" + packagingPrice +
                '}';
    }
}
