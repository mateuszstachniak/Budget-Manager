package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        BudgetManager budgetManager = new BudgetManager();
        budgetManager.menu();
    }

    public static class BudgetManager {
        private Double balance = 0.00;
        private Double foodSum = 0.00;
        private Double clothesSum = 0.00;
        private Double entertainmentSum = 0.00;
        private Double otherSum = 0.00;
        private Double totalSum = 0.00;

        final private Scanner scanner = new Scanner(System.in);
        HashMap<Category, ArrayList<Purchase>> categoryPurchaseMap = new HashMap<>();

        BudgetManager() {

        }

        public void menu() {

            do {
                menuCommands();
                String action = scanner.next();
                if (action.equals("1")) {
                    this.addIncome();
                } else if (action.equals("2")) {
                    this.addPurchase();
                } else if (action.equals("3")) {
                    this.showListOfPurchase();
                } else if (action.equals("4")) {
                    this.showBalance();
                } else if (action.equals("5")) {
                    this.saveToFile();
                } else if (action.equals("6")) {
                    this.loadPurchasesFromFile();
                } else if (action.equals("0")) {
                    System.out.println();
                    System.out.println("Bye!");
                    System.exit(0);
                }
            } while (true);
        }

        public void menuCommands() {
            System.out.println("1) Add income");
            System.out.println("2) Add purchase");
            System.out.println("3) Show list of purchases");
            System.out.println("4) Balance");
            System.out.println("5) Save");
            System.out.println("6) Load");
            System.out.println("0) Exit");
            System.out.println();
        }

        private void addIncome() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter income:");
            Double addition = Double.parseDouble(scanner.nextLine());
            balance += addition;
            System.out.println("Income was added!");
        }

        private void addPurchase() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1) Food");
            System.out.println("2) Clothes");
            System.out.println("3) Entertainment");
            System.out.println("4) Other");
            System.out.println("5) Back");
            System.out.println();

            String action = scanner.nextLine();
            boolean purchaseContinue = true;

            do {
                if (action.equals("1") || action.equals("2") ||
                        action.equals("3") || action.equals("4")) {
                    purchaseContinue = false;
                } else if (action.equals("5")) {
                    System.out.println();
                    this.menu();
                } else {
                    System.out.println("Wrong number of answer!");
                    this.menu();
                }
            } while (purchaseContinue);

            Category categoryOfPurchase = Category.returnNewCategory(action);

            System.out.println("Enter purchase name:");
            String nameOfPurchase = scanner.nextLine();
            System.out.println("Enter its price:");
            Double priceOfPurchase = Double.parseDouble(scanner.nextLine());
            String priceInString = String.format("%.2f", priceOfPurchase);
            balance -= priceOfPurchase;
            totalSum += priceOfPurchase;

            if (action.equals("1")) {
                foodSum += priceOfPurchase;
            } else if (action.equals("2")) {
                clothesSum += priceOfPurchase;
            } else if (action.equals("3")) {
                entertainmentSum += priceOfPurchase;
            } else if (action.equals("4")) {
                otherSum += priceOfPurchase;
            }

            Purchase thisPurchase = new Purchase(categoryOfPurchase, nameOfPurchase, priceInString);

            if (!categoryPurchaseMap.containsKey(categoryOfPurchase)) {
                ArrayList<Purchase> purchases = new ArrayList<>();
                purchases.add(thisPurchase);
                categoryPurchaseMap.put(categoryOfPurchase, purchases);
            } else {
                categoryPurchaseMap.get(categoryOfPurchase).add(thisPurchase);
            }

            System.out.println("Purchase was added!");
            System.out.println();
            this.addPurchase();
        }

        private void showListOfPurchase() {
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            boolean showListContinue = true;
            if (categoryPurchaseMap.isEmpty()) {
                System.out.println("Purchase list is empty!");
                System.out.println();
                return;
            }

            do {
                System.out.println("Choose the type of purchases\n" +
                        "1) Food\n" +
                        "2) Clothes\n" +
                        "3) Entertainment\n" +
                        "4) Other\n" +
                        "5) All\n" +
                        "6) Back");

                String answer = scanner.nextLine();
                if (answer.equals("1")) {
                    if (!categoryPurchaseMap.containsKey(Category.FOOD)) {
                        System.out.println("Purchase list is empty!");
                        this.showListOfPurchase();
                    }
                    System.out.println("\nFood:");
                    for (Purchase purchase : categoryPurchaseMap.get(Category.FOOD)) {
                        System.out.println(purchase);
                    }
                    String foodSumInString = String.format("Total sum: $%.2f", foodSum);
                    System.out.println(foodSumInString);
                    this.showListOfPurchase();
                } else if (answer.equals("2")) {
                    if (!categoryPurchaseMap.containsKey(Category.CLOTHES)) {
                        System.out.println("Purchase list is empty!");
                        this.showListOfPurchase();
                    }
                    System.out.println("\nClothes:");
                    for (Purchase purchase : categoryPurchaseMap.get(Category.CLOTHES)) {
                        System.out.println(purchase);
                    }
                    String clothesSumInString = String.format("Total sum: $%.2f", clothesSum);
                    System.out.println("Total sum: $" + clothesSumInString);
                    this.showListOfPurchase();
                } else if (answer.equals("3")) {
                    if (!categoryPurchaseMap.containsKey(Category.ENTERTAINMENT)) {
                        System.out.println("Purchase list is empty!");
                        this.showListOfPurchase();
                    }
                    System.out.println("\nEntertainment:");
                    for (Purchase purchase : categoryPurchaseMap.get(Category.ENTERTAINMENT)) {
                        System.out.println(purchase);
                    }
                    String entertainmentSumInString = String.format("Total sum: $%.2f", entertainmentSum);
                    System.out.println(entertainmentSumInString);
                    this.showListOfPurchase();
                } else if (answer.equals("4")) {
                    if (!categoryPurchaseMap.containsKey(Category.OTHER)) {
                        System.out.println("Purchase list is empty!");
                        this.showListOfPurchase();
                    }
                    System.out.println("\nOther");
                    for (Purchase purchase : categoryPurchaseMap.get(Category.OTHER)) {
                        System.out.println(purchase);
                    }
                    String otherSumInString = String.format("Total sum: $%.2f", otherSum);
                    System.out.println("Total sum: $" + otherSumInString);
                    this.showListOfPurchase();
                } else if (answer.equals("5")) {
                    System.out.println("\nAll:");
                    for (ArrayList<Purchase> listOfAllPurchases : categoryPurchaseMap.values()) {
                        for (Purchase purchase : listOfAllPurchases) {
                            System.out.println(purchase);
                        }
                    }
                    String totalSumInString = String.format("%.2f", totalSum);
                    System.out.println("Total sum: $" + totalSumInString);
                    this.showListOfPurchase();
                } else if (answer.equals("6")) {
                    System.out.println();
                    this.menu();
                }
            } while (showListContinue);
        }

        private void showBalance() {
            String balanceInString = String.format("Balance: $%.2f", balance);
            System.out.println(balanceInString);
            System.out.println();

        }

        private void saveToFile() {
            try (FileWriter writer = new FileWriter("purchases.txt")) {
                writer.write(String.valueOf(balance) + "\n");
                writer.write(String.valueOf(totalSum) + "\n");
                writer.write(String.valueOf(foodSum) + "\n");
                writer.write(String.valueOf(clothesSum) + "\n");
                writer.write(String.valueOf(entertainmentSum) + "\n");
                writer.write(String.valueOf(otherSum) + "\n");

                for (Category category : categoryPurchaseMap.keySet()) {
                    for (Purchase purchase : categoryPurchaseMap.get(category)) {
                        writer.write(category.getId() + "\n");
                        writer.write(purchase.getName() + "\n");
                        writer.write(String.valueOf(purchase.getPrice()) + "\n");
                    }
                }
                System.out.println("Purchases were saved!");
                System.out.println();

            } catch (IOException e) {
                System.out.printf("An exception occurs %s", e.getMessage());
            }

        }

        private void loadPurchasesFromFile() {
            File file = new File("purchases.txt");

            try (Scanner scanner = new Scanner(file)) {
                balance = Double.parseDouble(scanner.nextLine());
                totalSum = Double.parseDouble(scanner.nextLine());
                foodSum = Double.parseDouble(scanner.nextLine());
                clothesSum = Double.parseDouble(scanner.nextLine());
                entertainmentSum = Double.parseDouble(scanner.nextLine());
                otherSum = Double.parseDouble(scanner.nextLine());

                while (scanner.hasNext()) {
                    String categoryId = scanner.nextLine();
                    Category categoryOfPurchase = Category.returnNewCategory(categoryId);

                    String purchaseName = scanner.nextLine();

                    String costInString = scanner.nextLine();
                    costInString = costInString.replaceAll(",", ".");
                    Double purchaseCost = Double.parseDouble(costInString);


                    Purchase thisPurchase = new Purchase(categoryOfPurchase, purchaseName, costInString);


                    if (!categoryPurchaseMap.containsKey(categoryOfPurchase)) {
                        ArrayList<Purchase> purchases = new ArrayList<>();
                        purchases.add(thisPurchase);
                        categoryPurchaseMap.put(categoryOfPurchase, purchases);
                    } else {
                        categoryPurchaseMap.get(categoryOfPurchase).add(thisPurchase);
                    }

                }
                System.out.println("Purchases were loaded!");
                System.out.println();

            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + "purchases.txt");
            }
        }
    }

    public static class Purchase {
        private Category category;
        private String name;
        private String price;

        public Purchase(Category category, String name, String price) {
            this.category = category;
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + " $" + price;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }
    }

    public enum Category {
        FOOD(1, "Food"),
        CLOTHES(2, "Clothes"),
        ENTERTAINMENT(3, "Entertainment"),
        OTHER(4, "Other");

        private int id;
        private String name;

        Category(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name + ":";
        }

        static public Category returnNewCategory(String id) {
            if (id.equals("1")) return Category.FOOD;
            if (id.equals("2")) return Category.CLOTHES;
            if (id.equals("3")) return Category.ENTERTAINMENT;
            else return Category.OTHER;
        }
    }

}