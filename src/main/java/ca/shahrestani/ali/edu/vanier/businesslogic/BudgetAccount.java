package ca.shahrestani.ali.edu.vanier.businesslogic;

import java.util.List;

public class BudgetAccount extends Account {
    private final double initialFund;

    public BudgetAccount(String name) {
        this(null, name, null, 0.00, 0.00);
    }

    public BudgetAccount(String name, double initialFund) {
        this(null, name, null, initialFund, initialFund);
    }

    public BudgetAccount(String id, String name, List<Transaction> transactionList, double balance, double initialFund) {
        super(id, name, transactionList, balance);
        this.initialFund = initialFund;
    }

    /* OVERRIDE METHODS */

    @Override
    public String toString() {
        return "BudgetAccount{" +
                "initialFund=" + initialFund +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", transactionList=" + transactionList +
                ", balance=" + balance +
                '}';
    }

    /* GETTERS & SETTERS */

    public Double getInitialFund() {
        return initialFund;
    }

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class BudgetAccountFactory extends Account.AccountFactory<BudgetAccount> {
        @Override
        public BudgetAccount load(String str) {
            return null;
        }
    }
}
