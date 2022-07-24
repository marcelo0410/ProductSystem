package com.mstudio;

import com.mstudio.IO.ProductGenerator;
import com.mstudio.entity.Product;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

public class Menu {
    public static void main(String[] args) {
        start();
    }

    public static List<Product> productList;
    public static double total;
    public static HashMap<Integer, int[]> bundleNumber;

    static {
        ProductGenerator productGenerator = new ProductGenerator();
        productList = productGenerator.getProductList();
        total = 0;
        bundleNumber = new HashMap<>();
    }

    public static void start(){
        Scanner sc = new Scanner(System.in);
        while(true){
            displayMenu();
            String option = sc.nextLine();
            Integer integer = validateMenuOption(option);
            switch (integer) {
                case 1:
                    startOrder();
                case 2:
                    startAdminMode();
                case -1:
                    break;
                case 3:
                    return;
            }
        }
    }

    public static Integer validateMenuOption(String option){
        if(option.equals("1")){
            return 1;
        } else if(option.equals("2")){
            return 2;
        } else if (option.equals("3")){
            return 3;
        } else{
            System.out.println("Please enter 1-3!");
            return -1;
        }
    }

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

    public static String[] menuOption(){
        String[] option = { "1. Order Grocery", "2. Admin Mode", "3. Exit"};
        return option;
    }

    public static void startOrder(){
        Scanner sc = new Scanner(System.in);
        while(true){
            displayOrderMenu();
            String groceryOption = sc.nextLine();
            Integer groceryOptionNum = Integer.parseInt(groceryOption);
            if(groceryOptionNum > productList.size() || groceryOptionNum < 0 || total == 0){
                System.out.println("Please choose between 1-" + productList.size());
                break;
            }
            if(groceryOptionNum > 0 && groceryOptionNum < productList.size()){
                displayProductInfo(groceryOptionNum);
                String quanOption = sc.nextLine();
                int quanOptionNum = Integer.parseInt(quanOption);
                calculateMoney(groceryOptionNum, quanOptionNum);
            }
            if(groceryOptionNum == 4){

            }

        }

    }

    public static void displayOrderMenu(){
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

    public static void displayProductInfo(int option){
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

    public static int[] calculateMoney(Integer groceryOption, Integer quanOption){
        Product product = productList.get(groceryOption-1);
        Map<Integer, Double> packagingPrice = product.getPackagingPrice();
        List<Integer> packagingKey = new ArrayList<>(packagingPrice.keySet());
        Collections.sort(packagingKey, Collections.reverseOrder());
        int temp = quanOption;
        double tempTotal = 0;
        int[] bundleArray = new int[packagingKey.size()+1];
        // return null array while no bundle price in this product
        if(packagingKey.size() == 0){
            tempTotal = product.getPrice() * quanOption;
            bundleNumber.put(groceryOption, bundleArray);
            System.out.println(quanOption + " package of " + 1 + " item ($" + product.getPrice() + " each)");
            System.out.println(product.getName() + " $" + tempTotal);
            System.out.println("Total: $" + total);

            return bundleArray;
        }

        for(int i = 0; i < packagingKey.size(); i++){
            bundleArray[i] = temp / packagingKey.get(i);
            if(bundleArray[i] != 0){
                System.out.println(bundleArray[i] + " package of " + packagingKey.get(i) + " items ($" + packagingPrice.get(packagingKey.get(i)) + " each)");
                tempTotal += packagingPrice.get(packagingKey.get(i)) * bundleArray[i];
            }
            temp = temp % packagingKey.get(i);
            if(temp == 0){
                break;
            }

        }
        if(temp != 0){
            bundleArray[packagingKey.size()] = temp;
            System.out.println(bundleArray[packagingKey.size()] + " package of " + 1 + " item ($" + product.getPrice() + " each)");
            tempTotal += temp*product.getPrice();

        }
        total += tempTotal;
        System.out.println(product.getName() + " $" + tempTotal);
        System.out.println("Total: $" + total);
        System.out.println();

        bundleNumber.put(groceryOption-1, bundleArray);
        return bundleArray;
    }

    public static void viewCurrentOrder(){
        System.out.println("-------------------");
        System.out.println("     Your Order");
        System.out.println("-------------------");
        for (Map.Entry<Integer, int[]> set : bundleNumber.entrySet()) {

            System.out.println(set.getKey() + " = " + set.getValue());
        }
    }

    public static void checkOut(){
        System.out.println("-------------------");
        System.out.println("     Receipt");
        System.out.println("-------------------");
        System.out.println(bundleNumber);
        for (Map.Entry<Integer, int[]> set : bundleNumber.entrySet()) {
            productList.get(set.getKey()).getPackagingPrice();
            System.out.println(set.getKey() + " = " + set.getValue());
        }
    }

    public static void startAdminMode(){

    }
}
