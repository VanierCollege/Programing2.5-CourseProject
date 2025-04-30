package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

public class Project implements Savable {

    /* SAVABLE METHODS */

    @Override
    public String export() {
        return "";
    }

    public static class ProjectFactory implements SavableFactory<Project> {
        @Override
        public Project load(String str) {
            return null;
        }
    }
}
