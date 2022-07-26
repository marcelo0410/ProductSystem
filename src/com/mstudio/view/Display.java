package com.mstudio.view;

import com.mstudio.entity.Product;

import java.util.List;

public class Display {
    /**
     * Display the main menu and its options
     */
    public static void displayMenu(){
        System.out.println("---------------------------");
        System.out.println("Welcome to BGL Order System");
        System.out.println("---------------------------");
        System.out.println("");
        for(String s : menuOption()){
            System.out.println(s);
        }
        System.out.println("");
        System.out.println("Please choose an option: ");
    }

    /**
     * All the options of menu
     *
     * @return options
     */
    public static String[] menuOption(){
        String[] option = { "1. Order Grocery", "2. Admin Mode", "3. Exit"};
        return option;
    }

    /**
     * Display order's menu and its options
     */
    public static void displayOrderMenu(List<Product> productList, double total){
        System.out.println("---------------------------");
        System.out.println("        Order System");
        System.out.println("---------------------------");
        System.out.println("");
        if(total != 0){
            System.out.println("Total: $" + total);
        }
        System.out.println("");
        System.out.println("Grocery list:");
        int count = 0;
        for(Product p: productList){
            System.out.println(++count+". "+p.getName()+" $"+ p.getPrice());
        }

        if(total != 0){
            System.out.println(++count + ". Checkout");
            System.out.println(++count + ". Cancel Order");
        }
        System.out.println("Please choose an option: ");

    }

    /**
     * Display one product's information (name, code and price)
     *
     * @param option
     */
    public static void displayProductInfo(int option, List<Product> productList){
        Product product = productList.get(--option);
        System.out.println("---------------------------");
        System.out.println("        Order System");
        System.out.println("---------------------------");
        System.out.println("");
        System.out.println("Name: "+product.getName());
        System.out.println("Price: $"+product.getPrice());
        System.out.println("");
        System.out.println("Bundle Price: ");
        product.displayBundlePrice();
        if(product.getPackagingPrice().isEmpty()){
            System.out.println("No bundle price available.");
        }
        System.out.println("");
        System.out.println("Please enter the quantity: ");
    }

    /**
     * Display admin mode's menu and options
     */
    public static void displayAdminOption(){
        System.out.println("---------------------------");
        System.out.println("        Admin Mode");
        System.out.println("---------------------------");
        System.out.println("1. Create Product");
        System.out.println("2. Update Product");
        System.out.println("3. Delete Product");
        System.out.println("4. View Product");
        System.out.println("5. Back to menu");
        System.out.println("Please choose an option: ");
    }
}
