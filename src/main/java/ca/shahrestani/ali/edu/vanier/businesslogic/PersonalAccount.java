package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

public class PersonalAccount extends Account<PersonalAccount> {

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
