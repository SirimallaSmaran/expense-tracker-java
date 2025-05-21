package tracker;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseTracker {
    private List<Transaction> transactions = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n1. Add Transaction\n2. View Monthly Summary\n3. Load From File\n4. Save To File\n5. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: addTransaction(); break;
                case 2: showSummary(); break;
                case 3: loadFromFile(); break;
                case 4: saveToFile(); break;
                case 5: return;
            }
        }
    }

    private void addTransaction() {
    	 Transaction.Type type = null;

    	    while (type == null) {
    	        System.out.print("Enter type (income/expense): ");
    	        String typeInput = scanner.nextLine().trim().toUpperCase();

    	        try {
    	            type = Transaction.Type.valueOf(typeInput);
    	        } catch (IllegalArgumentException e) {
    	            System.out.println("Invalid type! Please enter 'income' or 'expense'.");
    	        }
    	    }

    	    System.out.print("Enter category (e.g., Salary, Food, Rent, Travel): ");
    	    String category = scanner.nextLine();

    	    double amount = 0;
    	    while (true) {
    	        System.out.print("Enter amount: ");
    	        try {
    	            amount = Double.parseDouble(scanner.nextLine());
    	            if (amount <= 0) {
    	                System.out.println("Amount must be positive.");
    	                continue;
    	            }
    	            break;
    	        } catch (NumberFormatException e) {
    	            System.out.println("Invalid amount. Please enter a numeric value.");
    	        }
    	    }

    	    transactions.add(new Transaction(type, category, amount, LocalDate.now()));
    	    System.out.println("Transaction added.");
    }

    private void showSummary() {
        Map<YearMonth, List<Transaction>> grouped = transactions.stream()
            .collect(Collectors.groupingBy(t -> YearMonth.from(t.getDate())));

        for (YearMonth ym : grouped.keySet()) {
            System.out.println("\nSummary for " + ym);
            double income = 0, expense = 0;

            for (Transaction t : grouped.get(ym)) {
                if (t.getType() == Transaction.Type.INCOME) income += t.getAmount();
                else expense += t.getAmount();
            }

            System.out.println("Total Income: " + income);
            System.out.println("Total Expense: " + expense);
            System.out.println("Net: " + (income - expense));
        }
    }

    private void saveToFile() {
        System.out.print("Enter filename to save: ");
        String filename = scanner.nextLine();

        try {
            FileManager.saveToFile(transactions, filename);
            System.out.println("Saved to " + filename);
        } catch (Exception e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        System.out.print("Enter filename to load: ");
        String filename = scanner.nextLine();

        try {
            transactions.addAll(FileManager.loadFromFile(filename));
            System.out.println("Loaded transactions from " + filename);
        } catch (Exception e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}

