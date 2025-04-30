package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

public class Reimbursement implements Savable {

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class ReimbursementFactory implements SavableFactory<Reimbursement> {
        @Override
        public Reimbursement load(String str) {
            return null;
        }
    }
}
