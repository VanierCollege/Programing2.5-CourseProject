package ca.shahrestani.ali.edu.vanier.tool;

import ca.shahrestani.ali.edu.vanier.businesslogic.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.util.*;

/**
 * This class is a core component of the system and thus trusted to operate correctly.
 * <p>
 * Any issues found are considered critical system-level bugs.
 */
public final class DataManager {
    public static final String homeDir = System.getProperty("user.home");

    private static final Map<String, SavableFactory<? extends Savable>> factories = new HashMap<>();

    private DataManager() {}

    /**
     * Read the file contents of all the files in the directory.
     * Note that this method skips over any files unable to be read.
     *
     * @param dir the directory (path) to read
     * @param ext the file extension to read (without a period)
     * @return a map of file name to file data
     * @throws IOException any IO exception
     */
    public static Map<String, String> readAllFilesInDirectory(Path dir, String ext) throws IOException {
        Map<String, String> fileContents = new HashMap<>();

        if (Files.exists(dir) && Files.isDirectory(dir)) {
            try (DirectoryStream<Path> files = Files.newDirectoryStream(dir, "*." + ext)) {
                for (Path file : files) {
                    try {
                        String fileContent = Files.readString(file);
                        fileContents.put(String.valueOf(file.getFileName()), fileContent);
                    } catch (IOException _) {}
                }
            }
        } else {
            throw new FileNotFoundException(dir + " directory not found or is not a directory");
        }

        return fileContents;
    }

    /**
     * Read all the files of a file.
     *
     * @param file the file (path) to read
     * @return a list of line data
     * @throws IOException any IO exception
     */
    public static List<String> readAllLinesInFile(Path file) throws IOException{
        List<String> contents = new ArrayList<>();

        if (Files.exists(file) && Files.isRegularFile(file)) {
            contents = Files.readAllLines(file);
        } else {
            throw new FileNotFoundException(file + " file is not found is not a file");
        }

        return contents;
    }

    /* SAVABLE METHODS */

    /**
     * Register a class to be loadable in the {@code DataManager}
     *
     * @param type the (type) of class to be savable
     * @param factory the factory class implementing the loading
     * @param <T> the (type) of class to be savable
     */
    public static <T extends Savable> void registerFactory(Class<T> type, SavableFactory<T> factory) {
        factories.put(type.getSimpleName(), factory);
    }

    /**
     * Load a loadable class and construct a new instance of the class
     *
     * @param type the (type) of class to initialize
     * @param data the input data string
     * @param dependencies any extra data needed for initializing
     * @return the newly constructed object
     * @param <T> the (type) of class to initialize
     */
    public static <T extends Savable> T loadSavable(Class<T> type, String data, Map<String, Object> dependencies) {
        return loadSavable(type.getSimpleName(), data, dependencies);
    }

    /**
     * Load a loadable class and construct a new instance of the class
     *
     * @param type the (type) of class to initialize
     * @param data the input data string
     * @param dependencies any extra data needed for initializing
     * @return the newly constructed object
     * @param <T> the (type) of class to initialize
     */
    @SuppressWarnings("unchecked")
    public static <T extends Savable> T loadSavable(String type, String data, Map<String, Object> dependencies) {
        SavableFactory<T> factory = (SavableFactory<T>) factories.get(type);
        if (factory == null) {
            throw new UnsupportedOperationException("No loading factory registered for type: " + type);
        }
        try {
            return factory.load(data, dependencies);
        } catch (NullPointerException | IndexOutOfBoundsException | IllegalArgumentException | DateTimeException e) {
            // Example warning
            System.err.printf(
                    "Failed to load data of type %s: %s...%n" +
                    "(%s) %s) %n",
                    type, data.substring(0, Math.min(data.length(), 30)),
                    e.getClass().getSimpleName(), e.getMessage());
            return null;
        }
    }

    public static class SwitchFactorySignalException extends RuntimeException {
        public final String cls;

        public SwitchFactorySignalException(String cls) {
            super();
            this.cls = cls;
        }

        public SwitchFactorySignalException(Class<?> cls) {
            super();
            this.cls = cls.getSimpleName();
        }
    }

    /* GETTERS & SETTERS */

    public static Map<String, SavableFactory<? extends Savable>> getFactories() {
        return new HashMap<>(factories);
    }

    /* BUSINESS HANDLERS */

    public static final class OrganizationData {        // TODO: Make this an inner class in Organization
        // Global centralized data store to avoid object duplication during load
        public Map<String, User> userMap = new HashMap<>();
        public Map<String, Project> projectMap = new HashMap<>();
        public Map<String, Account> accountMap = new HashMap<>();
        public Map<String, Reimbursement> reimbursementMap = new HashMap<>();

        private final String orgName;

        public OrganizationData(String orgName) {
            this.orgName = orgName;
        }

        /**
         * Loads all the data from the organization's folder.
         *
         * @throws IOException any IO exception
         */
        public Organization loadData() throws IOException {
            Path orgDir = Paths.get(homeDir, "OBT", orgName);
            Files.createDirectories(orgDir);

            Path usersDir = orgDir.resolve("users");
            Path accountsDir = orgDir.resolve("accounts");
            Path projectsFile = orgDir.resolve("projects.obt.txt");
            Path reimbursementsFile = orgDir.resolve("reimbursements.obt.txt");

            Files.createDirectories(usersDir);
            Files.createDirectories(accountsDir);
            if (Files.notExists(projectsFile)) {
                Files.createFile(projectsFile);
            }
            if (Files.notExists(reimbursementsFile)) {
                Files.createFile(reimbursementsFile);
            }

            /* NOTES
            - File names, although corresponding to the object's IDs, are not used.

            - The following methods should be called in order. The loading sequence itself will not fail
            if they are not, but objects with dependencies will fail to be initialized at the last step
             (load factory). This will lead to missing data.
             */

            // 1. Load accounts first, needed for User and Project
            accountMap = loadAccounts(accountsDir);

            // 2. Load users, needed for Reimbursement
            userMap = loadUsers(usersDir);

            // 3. Load Project, needed for Reimbursement
            projectMap = loadProjects(projectsFile);

            // 4. Load Reimbursement
            reimbursementMap = loadReimbursements(reimbursementsFile);

            // 5. Inject Reimbursement to Organizer and Project

            injectReimbursements();

            return new Organization(
                    orgName,
                    new HashSet<>(userMap.values()),
                    new HashSet<>(projectMap.values()),
                    new HashSet<>(accountMap.values()),
                    new HashSet<>(reimbursementMap.values())
            );
        }

        /**
         * Loads all accounts from the 'accounts' subfolder.
         * Each file in the folder is expected to be a single account's data.
         * Note that this method expects to be called during a proper loading sequence.
         * Note that this method does not perform IO validity checks.
         *
         * @param accountsDir the path to the accounts directory
         * @return a map of AccountID to Account objects
         * @throws IOException any IO exception
         */
        private Map<String, Account> loadAccounts(Path accountsDir) throws IOException {
            Map<String, Account> accounts = new HashMap<>();

            Map<String, String> rawData = readAllFilesInDirectory(accountsDir, "obt.txt");
            for (String rawAccount : rawData.values()) {
                if (rawAccount.isEmpty()) {
                    continue;
                }

                Account account;
                try {
                    account = loadSavable(Account.class, rawAccount, null);
                } catch (SwitchFactorySignalException sfsE) {
                    account = loadSavable(sfsE.cls, rawAccount, null);
                }

                if (account != null) {
                    accounts.put(account.getId(), account);
                }
            }

            return accounts;
        }

        /**
         * Load all users from the 'users' subfolder.
         * Each file in the folder is expected to be a single user's data.
         * Note that this method expects to be called during a proper loading sequence.
         * Note that this method does not perform IO validity checks.
         *
         * @param usersDir the path to the users directory
         * @return a map of UserID to User objects
         * @throws IOException any IO exception
         */
        private Map<String, User> loadUsers(Path usersDir) throws IOException {
            Map<String, User> users = new HashMap<>();

            Map<String, Object> userFactoryDependencies = new HashMap<>();
            userFactoryDependencies.put("GLOBAL_ACCOUNTS", accountMap);

            Map<String, String> rawData = readAllFilesInDirectory(usersDir, "obt.txt");
            for (String rawUser : rawData.values()) {
                if (rawUser.isEmpty()) {
                    continue;
                }

                User user;
                try {
                    user = loadSavable(User.class, rawUser, null);
                } catch (SwitchFactorySignalException sfsE) {
                    user = loadSavable(sfsE.cls, rawUser, userFactoryDependencies); // Only Organizer needs the dependency anyway
                }

                if (user != null) {
                    users.put(user.getId(), user);
                }
            }

            return users;
        }

        /**
         * Load all projects from the 'projects' file.
         * Each line in the file is expected to be a single project's data.
         * Note that this method expects to be called during a proper loading sequence.
         * Note that this method does not perform any IO validity checks.
         *
         * @param projectsFile the path to the projects file
         * @return a map of ProjectName to Project object
         * @throws IOException any IO exception
         */
        private Map<String, Project> loadProjects(Path projectsFile) throws IOException {
            Map<String, Project> projects = new HashMap<>();

            Map<String, Object> projectFactoryDependencies = new HashMap<>();
            projectFactoryDependencies.put("GLOBAL_ACCOUNTS", accountMap);

            List<String> rawData = readAllLinesInFile(projectsFile);
            for (String rawProject : rawData) {
                if (rawProject.isEmpty()) {
                    continue;
                }

                Project project = loadSavable(Project.class, rawProject, projectFactoryDependencies);

                if (project != null) {
                    projects.put(project.getName(), project);
                }
            }

            return projects;
        }

        /**
         * Load all reimbursements from the 'reimbursements' file.
         * Each line in the file is expected to be a single reimbursement's data.
         * Note that this method expects to be called during a proper loading sequence.
         * Note that this method does not perform IO validity checks.
         *
         * @param reimbursementsFile the path to the reimbursements file
         * @return a map of ReimbursementID to Reimbursement object
         * @throws IOException any IO exception
         */
        private Map<String, Reimbursement> loadReimbursements(Path reimbursementsFile) throws IOException {
            Map<String, Reimbursement> reimbursements = new HashMap<>();

            Map<String, Object> reimbursementFactoryDependencies = new HashMap<>();
            reimbursementFactoryDependencies.put("GLOBAL_USERS", userMap);
            reimbursementFactoryDependencies.put("GLOBAL_PROJECTS", projectMap);

            List<String> rawData = readAllLinesInFile(reimbursementsFile);
            for (String rawReimbursement : rawData) {
                if (rawReimbursement.isEmpty()) {
                    continue;
                }

                Reimbursement reimbursement = loadSavable(Reimbursement.class, rawReimbursement, reimbursementFactoryDependencies);

                if (reimbursement != null) {
                    reimbursements.put(reimbursement.getId(), reimbursement);
                }
            }

            return reimbursements;
        }

        /**
         * Inject reimbursements into corresponding lists in Organizer and Project objects
         */
        private void injectReimbursements() {
            for (Reimbursement reimbursement : reimbursementMap.values()) {
                boolean completed = !reimbursement.getStatus().equals(ReimbursementStatus.PENDING);

                Organizer organizer = reimbursement.getRequester();
                Project project = reimbursement.getProject();

                if (completed) {
                    organizer.recordCompletedReimbursement(reimbursement);
                    project.recordCompletedReimbursement(reimbursement);
                } else {
                    organizer.recordPendingReimbursement(reimbursement);
                    project.recordPendingReimbursement(reimbursement);
                }
            }

        }
    }
}
