package tracker;

import java.io.*;
import java.util.*;

public class FileManager {
    public static void saveToFile(List<Transaction> transactions, String filename) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Transaction t : transactions) {
                pw.println(t);
            }
        }
    }

    public static List<Transaction> loadFromFile(String filename) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                transactions.add(Transaction.fromString(line));
            }
        }
        return transactions;
    }
}
