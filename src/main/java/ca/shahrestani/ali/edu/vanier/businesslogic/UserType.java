package ca.shahrestani.ali.edu.vanier.businesslogic;

public enum UserType {
    ORGANIZER(5),
    TREASURER(2),
    SYSTEM_ADMINISTRATOR(0);

    final int level;

    UserType(int level) {
        this.level = level;
    }
}
