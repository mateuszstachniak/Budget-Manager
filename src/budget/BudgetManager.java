package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class BudgetManager {
    private Double balance = 0.00;
    private Double foodSum = 0.00;
    private Double clothesSum = 0.00;
    private Double entertainmentSum = 0.00;
    private Double otherSum = 0.00;
    private Double totalSum = 0.00;

    final private Scanner scanner = new Scanner(System.in);
    Map<Category, ArrayList<Purchase>> categoryPurchaseMap = new HashMap<>();

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
            } else if (action.equals("7")) {
                this.analyze();
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
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
        System.out.println();
    }

    private void addIncome() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter income:");
        Double addition = Double.parseDouble(scanner.nextLine());
        balance += addition;
        System.out.println("Income was added!");
        System.out.println();
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

    private void analyze() {
        boolean analyzeContinue = true;
        System.out.println(
                "How do you want to sort?\n" +
                        "1) Sort all purchases\n" +
                        "2) Sort by type\n" +
                        "3) Sort certain type\n" +
                        "4) Back");

        do {
            String answer = scanner.nextLine();
            if (answer.equals("1")) {
                if (categoryPurchaseMap.isEmpty()) {
                    System.out.println();
                    System.out.println("Purchase list is empty!");
                    System.out.println();
                    this.analyze();
                } else {
                    System.out.println();
                    this.sortAllPurchase();
                }
            } else if (answer.equals("2")) {
                System.out.println();
                this.sortByType();
            } else if (answer.equals("3")) {
                System.out.println();
                this.sortByCertainType();
            } else if (answer.equals("4")) {
                System.out.println();
                this.menu();
            }
        } while (analyzeContinue);
    }

    private void sortAllPurchase() {
        ArrayList<Purchase> allPurchases = new ArrayList<>();
        for (Category mapKey : categoryPurchaseMap.keySet()) {
            allPurchases.addAll(categoryPurchaseMap.get(mapKey));
        }

        allPurchases.sort(new PurchaseComparator());
        for (Purchase purchase : allPurchases) {
            System.out.println(purchase);
        }

        System.out.println();
        this.analyze();
    }

    private void sortByType() {
        System.out.println("Types");
        ArrayList<TypeSorter> listOfTypes = new ArrayList<>();
        listOfTypes.add(new TypeSorter("Food", foodSum));
        listOfTypes.add(new TypeSorter("Clothes", clothesSum));
        listOfTypes.add(new TypeSorter("Entertainment", entertainmentSum));
        listOfTypes.add(new TypeSorter("Other", otherSum));

        Collections.sort(listOfTypes);
        for (TypeSorter typeToSum : listOfTypes) {
            System.out.println(typeToSum);
        }

        System.out.println("Total sum: $" + totalSum);
        System.out.println();
        this.analyze();
    }

    private void sortByCertainType() {
        System.out.println(
                "Choose the type of purchase\n" +
                        "1) Food\n" +
                        "2) Clothes\n" +
                        "3) Entertainment\n" +
                        "4) Other");

        String answer = scanner.nextLine();
        if (!answer.equals("1") && !answer.equals("2") &&
                !answer.equals("3") && !answer.equals("4")) {
            System.out.println("Worng number");
            this.analyze();
        }
        System.out.println();

        Category category = Category.returnNewCategory(answer);
        if (!categoryPurchaseMap.containsKey(category)) {
            System.out.println();
            System.out.println("Purchase list is empty!");
            System.out.println();
            this.analyze();
        } else {

            System.out.println(category);
            ArrayList<Purchase> listOfPurchase = new ArrayList<>();
            listOfPurchase.addAll(categoryPurchaseMap.get(category));
            listOfPurchase.sort(new PurchaseComparator());

            for (Purchase purchase : listOfPurchase) {
                System.out.println(purchase);
            }
            System.out.println();


            this.analyze();
        }


    }
}
