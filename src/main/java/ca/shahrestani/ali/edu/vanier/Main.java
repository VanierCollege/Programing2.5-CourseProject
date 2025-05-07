package ca.shahrestani.ali.edu.vanier;

import ca.shahrestani.ali.edu.vanier.businesslogic.*;
import ca.shahrestani.ali.edu.vanier.tool.DataManager;

public class Main {
    public static void main(String[] args) {
    }

    /**
     * Register the classes that will be saved (export/load ability)
     */
    public static void registerBusinessFactories() {
//        DataManager.registerFactory(User.class, new User.UserFactory<>());
        DataManager.registerFactory(Organizer.class, new Organizer.OrganizerFactory());
        DataManager.registerFactory(Treasurer.class, new Treasurer.TreasurerFactory());
        DataManager.registerFactory(SystemAdministrator.class, new SystemAdministrator.SystemAdministratorFactory());

//        DataManager.registerFactory(Account.class, new Account.AccountFactory<>());
        DataManager.registerFactory(PersonalAccount.class, new PersonalAccount.PersonAccountFactory());
        DataManager.registerFactory(BudgetAccount.class, new BudgetAccount.BudgetAccountFactory());

        DataManager.registerFactory(Organization.class, new Organization.OrganizationFactory());
        DataManager.registerFactory(Project.class, new Project.ProjectFactory());

        DataManager.registerFactory(Transaction.class, new Transaction.TransactionFactory());
        DataManager.registerFactory(Reimbursement.class, new Reimbursement.ReimbursementFactory());
    }
}
