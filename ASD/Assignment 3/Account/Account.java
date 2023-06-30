import java.util.List;

// Customer.java
public class Customer {
    // Properties and methods of Customer
}

// AccountEntry.java
public class AccountEntry {
    // Properties and methods of AccountEntry
}

// AccountDAO.java
public class AccountDAO {
    public void saveAccount(Account account) {
        // Save account data
    }

    public void updateAccount(Account account) {
        // Update account data
    }

    public Account loadAccount(long accountNumber) {
        // Load account data
        return null;
    }

    public List<Account> getAccounts() {
        // Retrieve all accounts
        return null;
    }
}

// InterestStrategy.java
public interface InterestStrategy {
    double calculateInterest(double balance);
}

// HighInterestStrategy.java
public class HighInterestStrategy implements InterestStrategy {
    public double calculateInterest(double balance) {
        // Calculate high interest for the account
        // ...
        return calculatedInterest;
    }
}

// LowInterestStrategy.java
public class LowInterestStrategy implements InterestStrategy {
    public double calculateInterest(double balance) {
        // Calculate low interest for the account
        // ...
        return calculatedInterest;
    }
}

// Account.java
public abstract class Account {
    protected long accountNumber;
    protected double balance;
    protected Customer customer;

    public Account(long accountNumber, double balance, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
    }

    public void deposit(double amount) {
        // Deposit funds to the account
    }

    public void withdraw(double amount) {
        // Withdraw funds from the account
    }

    public double getBalance() {
        // Get the account balance
        return balance;
    }

    public void transferFunds(Account destinationAccount, double amount) {
        // Transfer funds from this account to the destination account
    }

    public abstract void addInterest();

    // Other methods and properties of Account
}

// SavingsAccount.java
public class SavingsAccount extends Account {
    private InterestStrategy interestStrategy;

    public SavingsAccount(long accountNumber, double balance, Customer customer) {
        super(accountNumber, balance, customer);
        interestStrategy = new HighInterestStrategy();
    }

    public void addInterest() {
        double interest = interestStrategy.calculateInterest(balance);
        balance += interest;
    }

    // Other methods specific to SavingsAccount
}

// CheckingAccount.java
public class CheckingAccount extends Account {
    private InterestStrategy interestStrategy;

    public CheckingAccount(long accountNumber, double balance, Customer customer) {
        super(accountNumber, balance, customer);
        interestStrategy = new LowInterestStrategy();
    }

    public void addInterest() {
        double interest = interestStrategy.calculateInterest(balance);
        balance += interest;
    }

    // Other methods specific to CheckingAccount
}

// AccountService.java
public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void createAccount(Account account) {
        // Create a new account
    }

    public void deposit(long accountNumber, double amount) {
        // Deposit funds to the account
    }

    public void withdraw(long accountNumber, double amount) {
        // Withdraw funds from the account
    }

    public void transferFunds(long sourceAccountNumber, long destinationAccountNumber, double amount) {
        // Transfer funds between accounts
    }

    public Account getAccount(long accountNumber) {
        // Get the account by account number
        return null;
    }

    public List<Account> getAllAccounts() {
        // Get all accounts
        return null;
    }
}

// Application.java
public class Application {
    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();
        AccountService accountService = new AccountService(accountDAO);

        Customer customer1 = new Customer("John Doe");
        SavingsAccount savingsAccount = new SavingsAccount(123456789, 1000.0, customer1);
        accountService.createAccount(savingsAccount);
        accountService.deposit(123456789, 500.0);

        Customer customer2 = new Customer("Jane Smith");
        CheckingAccount checkingAccount = new CheckingAccount(987654321, 2000.0, customer2);
        accountService.createAccount(checkingAccount);
        accountService.withdraw(987654321, 300.0);

        accountService.transferFunds(123456789, 987654321, 200.0);

        Account retrievedAccount = accountService.getAccount(123456789);
        retrievedAccount.addInterest();
    }
}
