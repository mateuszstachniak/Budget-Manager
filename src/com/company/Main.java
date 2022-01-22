package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BudgetManager budgetManager = new BudgetManager();
        budgetManager.menu();
    }

    static class BudgetManager {
        protected final Scanner scanner = new Scanner(System.in);
        ArrayList<String> purchasesList = new ArrayList<>();
        double total = 0.00;
        double sum = 0.00;

        public BudgetManager() {
            
        }

        private void menu() {
            do {
                menuCommands();
                String action = scanner.next();
                if (action.equals("1")) {
                    addIncome();
                } else if (action.equals("2")) {
                    addPurchase();
                } else if (action.equals("3")) {
                    showList();
                } else if (action.equals("4")) {
                    balance();
                } else if (action.equals("0")) {
                    System.out.println();
                    System.out.println("Bye!");
                    break;
                }
            } while (true);
        }

        private static void menuCommands() {
            System.out.println("1) Add income");
            System.out.println("2) Add purchase");
            System.out.println("3) Show list of purchases");
            System.out.println("4) Balance");
            System.out.println("0) Exit");
            System.out.println();
        }

        private double addIncome() {
            System.out.println("Enter income:");
            double income = scanner.nextDouble();
            System.out.println("Income was added!");
            System.out.println();
            return total = total + income;
        }

        private double addPurchase() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter purchase name:");
            String productName = scanner.nextLine();
            System.out.println("Enter its price:");
            String productPrice = scanner.nextLine();
            System.out.println("Purchase was added!\n");
            purchasesList.add(productName + " $" + productPrice);
            return sum = sum + Double.parseDouble(productPrice);
        }

        private void showList() {
            if (purchasesList.size() == 0) {
                System.out.println("The purchase list is empty\n");
            } else {
                for (int i = 0; i < purchasesList.size(); i++) {
                    System.out.println(purchasesList.get(i));
                }
                System.out.println(String.format("Total sum: $%.2f\n", sum));
            }
        }

        private void balance() {
            if (total - sum > 0) {
                System.out.println(String.format("Balance: $%.2f\n", total - sum));
            } else {
                System.out.println(String.format("Balance: $%.2f\n", total));
            }
        }
    }
}