package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

public class Transaction implements Savable {

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class TransactionFactory implements SavableFactory<Transaction> {
        @Override
        public Transaction load(String str) {
            return null;
        }
    }
}
