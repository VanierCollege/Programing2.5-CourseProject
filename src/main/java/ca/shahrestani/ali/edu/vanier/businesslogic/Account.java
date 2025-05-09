package ca.shahrestani.ali.edu.vanier.businesslogic;

import ca.shahrestani.ali.edu.vanier.tool.DataManager;
import ca.shahrestani.ali.edu.vanier.tool.Savable;
import ca.shahrestani.ali.edu.vanier.tool.SavableFactory;
import ca.shahrestani.ali.edu.vanier.tool.Util;

import java.util.*;

public abstract class Account implements Savable, Comparable<Account> {
    protected final String id;
    protected String name;
    protected Collection<Transaction> transactionList;
    protected double balance;

    public Account(String name) {
        this(null, name, null, 0.00);
    }

    public Account(String name, Double balance) {
        this(null, name, null, balance);
    }

    public Account(String id, String name, List<Transaction> transactionList, double balance) {
        this.id = Objects.requireNonNullElse(id,
                Util.generateID(6, false, false, true)
        );
        this.name = Objects.requireNonNull(name);
        this.transactionList = Objects.requireNonNullElse(transactionList, new LinkedHashSet<>());
        this.balance = balance;
    }

    /**
     * Record a transaction into the account's history.
     * <p></p>
     * Supports {@code DEPOSIT}, {@code REIMBURSEMENT}, {@code EXPENSE}.
     *
     * @param transaction the transaction to record
     * @return the updated balance
     * @throws IllegalArgumentException for other transaction types
     */
    public double recordTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction);
        double sign = switch (transaction.getType()) {
            case DEPOSIT, REIMBURSEMENT -> +1;
            case EXPENSE -> -1;
            case null, default -> throw new IllegalArgumentException("Invalid transaction type for this method: " + transaction.getType());
        };

        boolean change = transactionList.add(transaction);
        if (change) {
            balance += sign * transaction.getAmount();
        }

        return balance;
    }

    /**
     * Transfer a balance <b>TO</b> another account. The other account gets their balance increased.
     * <p></p>
     * The transfer balance is recorded as a {@code DEPOSIT} in the other account.
     *
     * @param transaction the transfer to perform
     * @param recipient the receiving account
     * @return the updated balance (of sender)
     */
    public double transfer(Transaction transaction, Account recipient) {
        Objects.requireNonNull(transaction);
        if (!transaction.getType().equals(TransactionType.TRANSFER)) {
            throw new IllegalArgumentException("Invalid transaction type for this method: " + transaction.getType());
        }
        Objects.requireNonNull(recipient);

        boolean change = transactionList.add(transaction);
        if (change) {
            balance += -1 * transaction.getAmount();
        }
        Transaction deposit = new Transaction(
                TransactionType.DEPOSIT,
                String.format("TRANSFER (tID:[%s]) FROM EXTERNAL ACCOUNT (aID:[%s])", transaction.getId(), id),
                transaction.getAmount()
        );
        change = recipient.transactionList.add(deposit);
        if (change) {
            recipient.balance += +1 * deposit.getAmount();
        }

        return balance;
    }

    /**
     * Return the account's transaction matching the filter type.
     *
     * @param type the type of transactions to return
     * @return the list of transactions matching the type
     */
    public List<Transaction> getFilteredTransactions(TransactionType type) {
        if (type == null) {
            return getTransactionList();
        }
        return transactionList.stream()
                .filter(transaction -> transaction.getType().equals(type))
                .toList();
    }

    /**
     * Check if a (deduction) amount will result in a negative account balance. {@code balance - amount < 0}
     * <p></p>
     * Note that this method does not transfer any funds itself.
     *
     * @param amount the amount to potentially deduct
     * @return whether the account will  overdraft or not
     */
    public boolean willOverdraft(double amount) {
        return balance - amount < 0;
    }

    /* OVERRIDE METHODS */

    @Override
    public int compareTo(Account otherAccount) {
        return Double.compare(this.balance, otherAccount.balance);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(getName(), account.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", transactionList=" + transactionList +
                ", balance=" + balance +
                '}';
    }

    /* GETTERS & SETTERS */

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionList() {
        return new ArrayList<>(transactionList);
    }

    /* SAVABLE METHODS */

    public static class AccountFactory<T extends Account> implements SavableFactory<T> {
        @Override
        public T load(String str, Map<String, Object> dependencies) {
            String type = Util.elementAt(str.trim().split(","), 0, "INVALID");
            if (type.equals("INVALID")) {
                return null;
            } else {
                throw new DataManager.SwitchFactorySignalException(type);
            }
        }
    }
}
