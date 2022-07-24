package com.mstudio.IO;

import com.mstudio.entity.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductGenerator {
    final Double cheesePrice = 5.95;
    final String cheeseCode = "CE";
    final String cheeseName = "Cheese";

    final Double hamPrice = 7.95;
    final String hamCode = "HM";
    final String hamName = "Ham";

    final Double soyPrice = 11.95;
    final String soyCode = "SS";
    final String soyName = "Soy Sauce";

    List<Product> productList;

    public ProductGenerator() {
        productList = new ArrayList<>();
        Product p1 = new Product(cheeseName,cheeseCode,cheesePrice);
        HashMap<Integer, Double> p1Map = new HashMap<>();

        p1Map.put(3,14.95);
        p1Map.put(5,20.95);
        p1.setPackagingPrice(p1Map);
        Product p2 = new Product(hamName, hamCode, hamPrice);

        HashMap<Integer, Double> p2Map = new HashMap<>();
        p2Map.put(2,13.95);
        p2Map.put(5,29.95);
        p2Map.put(8,40.95);
        p2.setPackagingPrice(p2Map);


        Product p3 = new Product(soyName, soyCode, soyPrice);

        productList.add(p1);
        productList.add(p2);
        productList.add(p3);
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
