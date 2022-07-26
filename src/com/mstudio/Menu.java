package com.mstudio;

import com.mstudio.util.ProductGenerator;
import com.mstudio.entity.Product;
import com.mstudio.util.Validation;
import com.mstudio.view.Display;

import java.text.DecimalFormat;
import java.util.*;

import static com.mstudio.view.Display.displayAdminOption;
import static com.mstudio.view.Display.displayProductInfo;


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

    /**
     *  Main method to Start the program
     */
    public static void start(){
        Scanner sc = new Scanner(System.in);
        while(true){
            Display.displayMenu();
            String option = sc.nextLine();
            if(!Validation.checkString("[1-3]", option)){
                System.out.println("Please enter number between 1-3!");
                continue;
            }
            int optionInt = Integer.parseInt(option);
            switch (optionInt) {
                case 1:
                    startOrder();
                case 2:
                    startAdminMode();
                case 3:
                    return;
            }
        }
    }

    /**
     * Start the order functionality
     */
    public static void startOrder(){
        Scanner sc = new Scanner(System.in);
        while(true){
            Display.displayOrderMenu(productList, total);
            String groceryOption = sc.nextLine();

            if(total == 0){
                if(!Validation.checkString("[1-" + productList.size() + "]", groceryOption) ){
                    System.out.println("Please enter number between 1-" + productList.size() + "!");
                    continue;
                }
            } else {
                if(!Validation.checkString("[1-" + (productList.size()+2) + "]", groceryOption) ){
                    System.out.println("Please enter number between 1-" + (productList.size()+2) + "!");
                    continue;
                }
            }

            Integer groceryOptionNum = Integer.parseInt(groceryOption);
            if(groceryOptionNum > 0 && groceryOptionNum <= productList.size()){
                Display.displayProductInfo(groceryOptionNum, productList);
                String quanOption = sc.nextLine();
                int quanOptionNum = Integer.parseInt(quanOption);
                calculateMoney(groceryOptionNum, quanOptionNum);
            }
            if(groceryOptionNum == 4){
                checkOut();
                return;
            }
            if(groceryOptionNum == 5){
                total = 0;
                bundleNumber.clear();
                System.out.println("Order is cancelled successfully!");
                return;
            }

        }

    }


    /**
     * Calculate total and apply the bundle price calculation
     * @param groceryOption User's input option for choosing grocery
     * @param quanOption User's input option for quantity of one grocery
     * @return contains and records number of bundle purchased
     */
    public static double calculateMoney(Integer groceryOption, Integer quanOption){
        DecimalFormat df = new DecimalFormat("###.##");
        Product product = productList.get(groceryOption-1);
        Map<Integer, Double> packagingPrice = product.getPackagingPrice();
        List<Integer> packagingKey = new ArrayList<>(packagingPrice.keySet());
        Collections.sort(packagingKey, Collections.reverseOrder());
        int temp = quanOption;
        int preQuan = 0;
        double tempTotal = 0;
        int[] bundleArray = new int[packagingKey.size()+1];

        // return null array while no bundle price in this product
        if(packagingKey.size() == 0){


            if(bundleNumber.get(groceryOption - 1) != null){
                int[] preBundleNumber = bundleNumber.get(groceryOption-1);
                int preNumber = preBundleNumber[preBundleNumber.length - 1] * packagingKey.get(preBundleNumber.length - 1);
                temp += preNumber;
                total -= preNumber * product.getPrice();
            }
            tempTotal = Double.parseDouble(df.format(product.getPrice() * temp));
            bundleNumber.put(groceryOption-1, bundleArray);
            System.out.println(temp + " package of " + 1 + " item ($" + product.getPrice() + " each)");
            System.out.println(product.getName() + " $" + tempTotal);
            total += tempTotal;
            total = Double.parseDouble(df.format(total));
            System.out.println("Total: $" + total);
            return total;
        }

        // Bundle price product
        if(bundleNumber.get(groceryOption - 1) != null){
            int[] preBundleNumber = bundleNumber.get(groceryOption-1);
            for(int i = 0; i < preBundleNumber.length; i++){
                if(preBundleNumber[i] > 0 && i < packagingKey.size()-1){
                    preQuan += preBundleNumber[i] * packagingKey.get(i);
                    total -= preBundleNumber[i] * product.getPackagingPrice().get(packagingKey.get(i));
                }
            }
            if(preBundleNumber[preBundleNumber.length-1] != 0){
                preQuan += preBundleNumber[preBundleNumber.length-1];
                total -= preBundleNumber[preBundleNumber.length-1] * product.getPrice();
            }
            temp += preQuan;
        }
        for(int i = 0; i < packagingKey.size(); i++){
            bundleArray[i] = temp / packagingKey.get(i);
            if(bundleArray[i] != 0){
                System.out.println(bundleArray[i] + " package of " + packagingKey.get(i) + " items ($" + packagingPrice.get(packagingKey.get(i)) + " each)");
                tempTotal += Double.parseDouble(df.format(packagingPrice.get(packagingKey.get(i)) * bundleArray[i]));
            }
            temp = temp % packagingKey.get(i);
            if(temp == 0){
                break;
            }

        }
        if(temp != 0){
            bundleArray[packagingKey.size()] = temp;
            System.out.println(bundleArray[packagingKey.size()] + " package of " + 1 + " item ($" + product.getPrice() + " each)");
            tempTotal = Double.parseDouble(df.format(temp * product.getPrice()));
        }
        total += tempTotal;
        total = Double.parseDouble(df.format(total));
        System.out.println(product.getName() + " $" + tempTotal);
        System.out.println("Total: $" + total);
        System.out.println();

        bundleNumber.put(groceryOption-1, bundleArray);
        return total;
    }

    /**
     * Checkout method to print receipt
     */
    public static void checkOut(){
        System.out.println("-------------------");
        System.out.println("     Your Order");
        System.out.println("-------------------");
        for (Map.Entry<Integer, int[]> set : bundleNumber.entrySet()) {

            Product product = productList.get(set.getKey());
            Map<Integer, Double> packagingPrice = product.getPackagingPrice();
            List<Integer> packagingKey = new ArrayList<>(packagingPrice.keySet());
            Collections.sort(packagingKey, Collections.reverseOrder());
            System.out.println(product.getName());
            for(int i = 0; i<set.getValue().length; i++){


                if(set.getValue()[i] != 0 && packagingKey.size() > i){
                    System.out.println(set.getValue()[i] + " package of " + packagingKey.get(i) + " items ($" + packagingPrice.get(packagingKey.get(i)) + " each)");
                }
                if(i == set.getValue().length-1 && set.getValue()[i] > 0){
                    System.out.println(Arrays.toString(set.getValue()));
                    System.out.println(set.getValue()[i] + " package of 1 item ($" + packagingPrice.get(packagingKey.get(i)) + " each)");
                }
            }

        }
        System.out.println("Total: $" + total);
    }


    /**
     * Start the admin mode functionality
     */
    public static void startAdminMode(){
        Scanner sc = new Scanner(System.in);
        while(true){
            Display.displayAdminOption();
            String input = sc.nextLine();
            if(!Validation.checkString("[1-5]", input)){
                System.out.println("Please enter number between 1-5!");
                continue;
            }
            int i = Integer.parseInt(input);
            switch (i){
                case 1:
                    createProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    viewProduct();
                    break;
                case 5:
                    return;
            }
        }

    }



    /**
     * Create a new product
     */
    public static void createProduct(){
        Scanner sc = new Scanner(System.in);
        String productName;
        String productCode;
        String productPrice;
        while (true){
            System.out.println("---------------------------");
            System.out.println("        Admin Mode");
            System.out.println("    Create new product");
            System.out.println("---------------------------");

            while (true){
                System.out.println("Enter product name: ");
                productName = sc.nextLine();
                if(Validation.checkString("^[A-Za-z]+$", productName)){
                    break;
                }
                System.out.println("Please enter valid product name!");
            }

            while (true){
                System.out.println("Enter product code: ");
                productCode = sc.nextLine();
                if(Validation.checkString("^[A-Za-z]+$", productCode)){
                    break;
                }
                System.out.println("Please enter valid product code!");
            }

            while (true){
                System.out.println("Enter product price: ");
                productPrice = sc.nextLine();
                if(Validation.checkString("^\\d*\\.?\\d+$", productPrice)){
                    break;
                }
                System.out.println("Please enter valid product price!");
            }


            Product p1 = new Product(productName, productCode, Double.parseDouble(productPrice));
            productList.add(p1);
            System.out.println("***Product is added successfully!***");
            System.out.println("New Product Detail: ");
            System.out.println("Product Name: " + productName);
            System.out.println("Product Code: " + productCode);
            System.out.println("Product Price: " + productPrice);
            break;
        }
    }

    /**
     * Update one product
     */
    public static void updateProduct(){
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("---------------------------");
        System.out.println("        Admin Mode");
        System.out.println("      Update product");
        System.out.println("---------------------------");

        for(int i=0; i < productList.size(); i++){
            System.out.println(i+1 + ". " +productList.get(i).getName());
        }
        System.out.println();
        String s = sc.nextLine();
        int i = Integer.parseInt(s);
        updateProductProperty(i);
    }

    /**
     * Update method to execute
     * @param index product's index for updating
     */
    public static void updateProductProperty(int index){
        Scanner sc = new Scanner(System.in);
        String newProductName;
        String newProductCode;
        String newProductPrice;
        Product product = productList.get(index - 1);
        while (true){
            System.out.println("Old product name: " + product.getName());
            System.out.println("New product name: ");
            newProductName = sc.nextLine();
            if(Validation.checkString("^[A-Za-z]+$", newProductName)){
                break;
            }
            System.out.println("Please enter valid product name!");
        }

        while (true){
            System.out.println("Old product code: " + product.getCode());
            System.out.println("New product code: ");
            newProductCode = sc.nextLine();
            if(Validation.checkString("^[A-Za-z]+$", newProductCode)){
                break;
            }
            System.out.println("Please enter valid product code!");
        }

        while (true){
            System.out.println("Old product price: " + product.getPrice());
            System.out.println("New product price: ");
            newProductPrice = sc.nextLine();
            if(Validation.checkString("^\\d*\\.?\\d+$", newProductPrice)){
                break;
            }
            System.out.println("Please enter valid product price!");
        }

        double newProductPriceDouble = Double.parseDouble(newProductPrice);
        if(!newProductName.equals(product.getName())){
            product.setName(newProductName);
        }
        if(!newProductCode.equals(product.getCode())){
            product.setCode(newProductCode);
        }
        if(newProductPriceDouble != product.getPrice()){
            product.setPrice(newProductPriceDouble);
        }
        System.out.println("*** Product is updated successfully!***");
        System.out.println("Product Name: " + product.getName());
        System.out.println("Product Code: " + product.getCode());
        System.out.println("Product Price: " + product.getPrice());

    }

    /**
     * Delete one product in the system
     */
    public static void deleteProduct(){
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("---------------------------");
        System.out.println("        Admin Mode");
        System.out.println("      Delete product");
        System.out.println("---------------------------");

        for(int i=0; i<productList.size(); i++){
            System.out.println(i+1 + ". " +productList.get(i).getName());
        }
        System.out.println();
        String s = sc.nextLine();
        int i = Integer.parseInt(s);
        System.out.println("Product " + productList.get(i-1).getName() + " is removed!");
        productList.remove(i-1);
        System.out.println("");
    }

    /**
     * View all the products and select one specific product
     */
    public static void viewProduct(){
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("---------------------------");
        System.out.println("        Admin Mode");
        System.out.println("      View product");
        System.out.println("---------------------------");

        for(int i=0; i<productList.size(); i++){
            System.out.println(i+1 + ". " +productList.get(i).getName());
        }
        System.out.println();
        String s = sc.nextLine();
        int i = Integer.parseInt(s);
        Product product = productList.get(i - 1);
        System.out.println("Product Name: " + product.getName());
        System.out.println("Product Code: " + product.getCode());
        System.out.println("Product Price: $" + product.getPrice());

    }
}
