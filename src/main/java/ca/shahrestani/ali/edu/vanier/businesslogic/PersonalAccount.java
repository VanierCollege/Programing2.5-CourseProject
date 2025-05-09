package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.BracketAwareSplitter;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PersonalAccount extends Account {
    public PersonalAccount(String name) {
        super(name);
    }

    public PersonalAccount(String name, double balance) {
        super(name, balance);
    }

    public PersonalAccount(String id, String name, Set<Transaction> transactionList, double balance) {
        super(id, name, transactionList, balance);
    }

    /* OVERRIDE METHODS */

    @Override
    public String toString() {
        return "PersonalAccount{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", transactionList=" + transactionList +
                ", balance=" + balance +
                '}';
    }

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class PersonAccountFactory extends Account.AccountFactory<PersonalAccount> {
        @Override
        public PersonalAccount load(String str, Map<String, Object> dependencies) {
            // type, id, name, balance \n
            // ... transaction \n transaction ...

            String[] lines = str.split("\n");
            List<String> accountData = BracketAwareSplitter.splitIgnoringBrackets(lines[0]);

            String id = Util.requireStringNotEmpty(accountData.get(1));
            String name = Util.requireStringNotEmpty(accountData.get(2));
            double balance = Double.parseDouble(accountData.get(3));
            Set<Transaction> transactions = loadTransactions(lines);

            return new PersonalAccount(
                    id,
                    name,
                    transactions,
                    balance
            );
        }
    }
}
