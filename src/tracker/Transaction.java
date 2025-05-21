package tracker;

import java.time.LocalDate;

public class Transaction {
    public enum Type { INCOME, EXPENSE }
    private Type type;
    private String category;
    private double amount;
    private LocalDate date;

    public Transaction(Type type, String category, double amount, LocalDate date) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public Type getType() { return type; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }

    @Override
    public String toString() {
        return type + "," + category + "," + amount + "," + date;
    }

    public static Transaction fromString(String line) {
        String[] parts = line.split(",");
        return new Transaction(
            Type.valueOf(parts[0]),
            parts[1],
            Double.parseDouble(parts[2]),
            LocalDate.parse(parts[3])
        );
    }
}

