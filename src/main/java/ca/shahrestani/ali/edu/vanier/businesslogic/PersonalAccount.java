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

    public PersonalAccount(String name, List<Transaction> transactionList, Double balance) {
        super(name, transactionList, balance);
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
