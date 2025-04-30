package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

public class SystemAdministrator extends User<SystemAdministrator> {


    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class SystemAdministratorFactory extends UserFactory<SystemAdministrator> {
        @Override
        public SystemAdministrator load(String str) {
            return null;
        }
    }

    @Override
    public UserFactory<SystemAdministrator> getFactory() {
        return new SystemAdministratorFactory();
    }
}
