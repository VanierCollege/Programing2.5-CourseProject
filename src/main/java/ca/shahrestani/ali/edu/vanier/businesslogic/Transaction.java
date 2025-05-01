package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.time.ZonedDateTime;
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

    public Transaction(String id, TransactionType type, String description, Double amount, ZonedDateTime transactedAt) {
        this.id = Objects.requireNonNullElse(id,
                Util.generateID(8, true, false, true)
        );
        this.type = Objects.requireNonNull(type);
        this.description = Objects.requireNonNull(description);
        this.amount = Math.abs(Objects.requireNonNull(amount));
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
        public Transaction load(String str) {
            return null;
        }
    }
}
