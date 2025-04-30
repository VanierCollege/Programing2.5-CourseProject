package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

public abstract class User<S extends User<S>> implements Savable {

    public abstract static class UserFactory<T extends User<?>> implements SavableFactory<T> {
        @Override
        public abstract T load(String str);
    }

    public abstract UserFactory<S> getFactory();

}
