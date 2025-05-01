package ca.shahrestani.ali.edu.vanier.businesslogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BudgetAccount extends Account {
    private final Double initialFund;

    public BudgetAccount(String name) {
        this(name, new ArrayList<>(), 0.00, 0.00);
    }

    public BudgetAccount(String name, Double initialFund) {
        this(name, new ArrayList<>(), initialFund, initialFund);
    }

    public BudgetAccount(String name, List<Transaction> transactionList, Double balance, Double initialFund) {
        super(name, transactionList, balance);
        this.initialFund = Objects.requireNonNullElse(initialFund, 0.00);
    }

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
