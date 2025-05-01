package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Account implements Savable {
    protected String name;
    protected List<Transaction> transactionList;
    protected Double balance;

    public Account(String name) {
        this(name, new ArrayList<>(), 0.00);
    }

    public Account(String name, Double balance) {
        this(name, new ArrayList<>(), balance);
    }

    public Account(String name, List<Transaction> transactionList, Double balance) {
        this.name = name;
        this.transactionList = Objects.requireNonNullElse(transactionList, new ArrayList<>());
        this.balance = Objects.requireNonNullElse(balance, 0.00);
    }

    /**
     * Record a transaction into the account's history
     *
     * @param transaction the transaction to record
     */
    public void addTransaction(Transaction transaction) {
        transactionList.add(Objects.requireNonNull(transaction));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionList() {
        return new ArrayList<>(transactionList);
    }

    public abstract static class AccountFactory<T extends Account> implements SavableFactory<T> {
        @Override
        public abstract T load(String str);
    }
}
