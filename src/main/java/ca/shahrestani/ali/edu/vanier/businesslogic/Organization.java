package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

public class Organization implements Savable {

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class OrganizationFactory implements SavableFactory<Organization> {
        @Override
        public Organization load(String str) {
            return null;
        }
    }
}
