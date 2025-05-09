package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.BracketAwareSplitter;
import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Transaction implements Savable, Comparable<Transaction> {
    private final String id;
    private final TransactionType type;
    private final String description;
    private final double amount;
    private final ZonedDateTime transactedAt;

    public Transaction(TransactionType type, String description, Double amount) {
        this(null, type, description, amount, null);
    }

    public Transaction(String id, TransactionType type, String description, double amount, ZonedDateTime transactedAt) {
        this.id = Objects.requireNonNullElse(id,
                Util.generateID(8, true, false, true)
        );
        this.type = Objects.requireNonNull(type);
        this.description = Objects.requireNonNull(description);
        this.amount = Math.abs(amount);
        this.transactedAt = Objects.requireNonNullElse(transactedAt, Util.getNow());
    }

    /* OVERRIDE METHODS */

    @Override
    public int compareTo(Transaction otherTransaction) {
        return transactedAt.compareTo(otherTransaction.transactedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getId(), that.getId()) && getType() == that.getType() && Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getAmount());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", transactedAt=" + transactedAt +
                '}';
    }

    /* GETTERS & SETTERS */

    public String getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public ZonedDateTime getTransactedAt() {
        return transactedAt;
    }

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class TransactionFactory implements SavableFactory<Transaction> {
        @Override
        public Transaction load(String str, Map<String, Object> dependencies) {
            // id, type, description, amount, transactedAt

            List<String> transactionData = BracketAwareSplitter.splitIgnoringBrackets(str);

            String id = Util.requireStringNotEmpty(transactionData.get(0));
            TransactionType type = TransactionType.valueOf(transactionData.get(1));
            String description = transactionData.get(2);
            double amount = Double.parseDouble(transactionData.get(3));
            ZonedDateTime transactedAt = ZonedDateTime.parse(transactionData.get(4));

            return new Transaction(
                    id,
                    type,
                    description,
                    amount,
                    transactedAt
            );
        }
    }

    /* INNER IMPLEMENTATIONS */

    public static class TransactionComparator implements Comparator<Transaction> {
        private final SortType sort;

        public TransactionComparator(SortType sort) {
            this.sort = Objects.requireNonNull(sort);
        }

        @Override
        public int compare(Transaction o1, Transaction o2) {
            int amount = Double.compare(o1.getAmount(), o2.getAmount());
            int date = o1.getTransactedAt().toLocalDate().compareTo(o2.getTransactedAt().toLocalDate());
            switch (sort) {
                case AMOUNT -> amount *= 100;
                case DATE -> date *= 100;
            }
            return amount + date;
        }
    }
}
