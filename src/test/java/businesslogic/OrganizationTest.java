package businesslogic;

import ca.shahrestani.ali.edu.vanier.businesslogic.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrganizationTest {

    @Test
    public void testAddUser_new() {
        Organization organization = new Organization("Test");
        User user = SystemAdministrator.newDefaultSystemAdministrator("Test");
        Assertions.assertTrue(organization.addUser(user));
    }

    @Test
    public void testAddUser_duplicate() {
        Organization organization = new Organization("Test");
        User user = SystemAdministrator.newDefaultSystemAdministrator("Test");
        organization.addUser(user);
        Assertions.assertFalse(organization.addUser(user));
    }

    @Test
    public void testAddUser_null() {
        Organization organization = new Organization("Test");
        Assertions.assertThrows(NullPointerException.class, () -> organization.addUser(null));
    }

    @Test
    public void testRemoveUser_true() {
        Organization organization = new Organization("Test");
        User user = SystemAdministrator.newDefaultSystemAdministrator("Test");
        organization.addUser(user);
        Assertions.assertTrue(organization.removeUser(user));
    }

    @Test
    public void testRemoveUser_false() {
        Organization organization = new Organization("Test");
        User user = SystemAdministrator.newDefaultSystemAdministrator("Test");
        Assertions.assertFalse(organization.removeUser(user));
    }

    @Test
    public void testRemoveUser_null() {
        Organization organization = new Organization("Test");
        Assertions.assertThrows(NullPointerException.class, () -> organization.removeUser(null));
    }

    @Test
    public void testAddAccount_new() {
        Organization organization = new Organization("Test");
        Account account = new PersonalAccount("Test");
        Assertions.assertTrue(organization.addAccount(account));
    }

    @Test
    public void testAddAccount_duplicate() {
        Organization organization = new Organization("Test");
        Account account = new PersonalAccount("Test");
        organization.addAccount(account);
        Assertions.assertFalse(organization.addAccount(account));
    }

    @Test
    public void testAddAccount_null() {
        Organization organization = new Organization("Test");
        Assertions.assertThrows(NullPointerException.class, () -> organization.addAccount(null));
    }

    @Test
    public void testRemoveAccount_true() {
        Organization organization = new Organization("Test");
        Account account = new PersonalAccount("Test");
        organization.addAccount(account);
        Assertions.assertTrue(organization.removeAccount(account));
    }

    @Test
    public void testRemoveAccount_false() {
        Organization organization = new Organization("Test");
        Account account = new PersonalAccount("Test");
        Assertions.assertFalse(organization.removeAccount(account));
    }

    @Test
    public void testRemoveAccount_null() {
        Organization organization = new Organization("Test");
        Assertions.assertThrows(NullPointerException.class, () -> organization.removeAccount(null));
    }

    @Test
    public void testAddProject_new() {
        Organization organization = new Organization("Test");
        Project project = new Project("Test", "Test", null);
        Assertions.assertTrue(organization.addProject(project));
    }

    @Test
    public void testAddProject_duplicate() {
        Organization organization = new Organization("Test");
        Project project = new Project("Test", "Test", null);
        organization.addProject(project);
        Assertions.assertFalse(organization.addProject(project));
    }

    @Test
    public void testAddProject_null() {
        Organization organization = new Organization("Test");
        Assertions.assertThrows(NullPointerException.class, () -> organization.addProject(null));
    }

    @Test
    public void testRemoveProject_true() {
        Organization organization = new Organization("Test");
        Project project = new Project("Test", "Test", null);
        organization.addProject(project);
        Assertions.assertTrue(organization.removeProject(project));
    }

    @Test
    public void testRemoveProject_false() {
        Organization organization = new Organization("Test");
        Project project = new Project("Test", "Test", null);
        Assertions.assertFalse(organization.removeProject(project));
    }

    @Test
    public void testRemoveProject_null() {
        Organization organization = new Organization("Test");
        Assertions.assertThrows(NullPointerException.class, () -> organization.removeProject(null));
    }

    @Test
    public void testAddReimbursement_new() {
        Organization organization = new Organization("Test");
        Organizer organizer = new Organizer("Test");
        Project project = new Project("Test", "Test", null);
        Reimbursement reimbursement = new Reimbursement("Test", 0.00, organizer, project);
        Assertions.assertTrue(organization.addReimbursement(reimbursement));
    }

    @Test
    public void testAddReimbursement_duplicate() {
        Organization organization = new Organization("Test");
        Organizer organizer = new Organizer("Test");
        Project project = new Project("Test", "Test", null);
        Reimbursement reimbursement = new Reimbursement("Test", 0.00, organizer, project);
        organization.addReimbursement(reimbursement);
        Assertions.assertFalse(organization.addReimbursement(reimbursement));
    }

    @Test
    public void testAddReimbursement_null() {
        Organization organization = new Organization("Test");
        Assertions.assertThrows(NullPointerException.class, () -> organization.addReimbursement(null));
    }

    @Test
    public void testRemoveReimbursement_true() {
        Organization organization = new Organization("Test");
        Organizer organizer = new Organizer("Test");
        Project project = new Project("Test", "Test", null);
        Reimbursement reimbursement = new Reimbursement("Test", 0.00, organizer, project);
        organization.addReimbursement(reimbursement);
        Assertions.assertTrue(organization.removeReimbursement(reimbursement));
    }

    @Test
    public void testRemoveReimbursement_false() {
        Organization organization = new Organization("Test");
        Organizer organizer = new Organizer("Test");
        Project project = new Project("Test", "Test", null);
        Reimbursement reimbursement = new Reimbursement("Test", 0.00, organizer, project);
        Assertions.assertFalse(organization.removeReimbursement(reimbursement));
    }

    @Test
    public void testRemoveReimbursement_null() {
        Organization organization = new Organization("Test");
        Assertions.assertThrows(NullPointerException.class, () -> organization.removeReimbursement(null));
    }

}
