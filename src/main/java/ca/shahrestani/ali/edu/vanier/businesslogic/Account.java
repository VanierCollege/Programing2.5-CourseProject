package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;

public abstract class Account<S> implements Savable {

    public abstract static class AccountFactory<T extends Account<?>> implements SavableFactory<T> {
        @Override
        public abstract T load(String str);
    }
}
