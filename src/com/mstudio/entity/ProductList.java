package com.mstudio.entity;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    public Integer capacity;
    public List<Product> productList;

    public ProductList() {
        capacity = 0;
        productList = new ArrayList<>();
    }

}
