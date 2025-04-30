package ca.shahrestani.ali.edu.vanier.businesslogic;

public class BudgetAccount extends Account<BudgetAccount> {

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
