package ca.shahrestani.ali.edu.vanier;

import ca.shahrestani.ali.edu.vanier.businesslogic.*;
import ca.shahrestani.ali.edu.vanier.tool.DataManager;

import java.io.IOException;

public class Main {
    public static Organization organization; // The current active organization

    public static void main(String[] args) {
        registerBusinessFactories();

        // TODO: User input & interactivity...
        DataManager.OrganizationData orgData = new DataManager.OrganizationData("WHO");
        try {
            organization = orgData.loadData();
        } catch (IOException ioE) {
            System.err.println("Unable to load organization data!");
            System.err.println(ioE.getMessage());
            ioE.printStackTrace();
            System.exit(1);
        }

        // Small showcase of the system's loading data ability

        System.out.println(organization);
        System.out.println(organization.getAccounts());
        System.out.println(organization.getUsers());
        System.out.println(organization.getProjects());
        System.out.println(organization.getReimbursements());
    }

    /**
     * Register the classes that will be saved (export/load ability).
     */
    public static void registerBusinessFactories() {
        DataManager.registerFactory(User.class, new User.UserFactory<>());
        DataManager.registerFactory(Organizer.class, new Organizer.OrganizerFactory());
        DataManager.registerFactory(Treasurer.class, new Treasurer.TreasurerFactory());
        DataManager.registerFactory(SystemAdministrator.class, new SystemAdministrator.SystemAdministratorFactory());

        DataManager.registerFactory(Account.class, new Account.AccountFactory<>());
        DataManager.registerFactory(PersonalAccount.class, new PersonalAccount.PersonAccountFactory());
        DataManager.registerFactory(BudgetAccount.class, new BudgetAccount.BudgetAccountFactory());

        DataManager.registerFactory(Organization.class, new Organization.OrganizationFactory());
        DataManager.registerFactory(Project.class, new Project.ProjectFactory());

        DataManager.registerFactory(Transaction.class, new Transaction.TransactionFactory());
        DataManager.registerFactory(Reimbursement.class, new Reimbursement.ReimbursementFactory());
    }
}
