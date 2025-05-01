package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

import java.util.List;

public class PersonalAccount extends Account {
    public PersonalAccount(String name) {
        super(name);
    }

    public PersonalAccount(String name, Double balance) {
        super(name, balance);
    }

    public PersonalAccount(String id, String name, List<Transaction> transactionList, Double balance) {
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
        public PersonalAccount load(String str) {
            return null;
        }
    }
}
