package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.BracketAwareSplitter;
import ca.shahrestani.ali.edu.vanier.tool.Util;
import jdk.jfr.TransitionTo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BudgetAccount extends Account {
    private final double initialFund;

    public BudgetAccount(String name) {
        this(null, name, null, 0.00, 0.00);
    }

    public BudgetAccount(String name, double initialFund) {
        this(null, name, null, initialFund, initialFund);
    }

    public BudgetAccount(String id, String name, Set<Transaction> transactionList, double balance, double initialFund) {
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
        public BudgetAccount load(String str, Map<String, Object> dependencies) {
            // type, id, name, balance, initialBalance \n
            // ... transaction \n transaction ...

            String[] lines = str.split("\n");
            List<String> accountData = BracketAwareSplitter.splitIgnoringBrackets(lines[0]);

            String id = Util.requireStringNotEmpty(accountData.get(1));
            String name = Util.requireStringNotEmpty(accountData.get(2));
            double balance = Double.parseDouble(accountData.get(3));
            double initialBalance = Double.parseDouble(accountData.get(4));
            Set<Transaction> transactions = loadTransactions(lines);

            return new BudgetAccount(
                    id,
                    name,
                    transactions,
                    balance,
                    initialBalance
            );
        }
    }
}
