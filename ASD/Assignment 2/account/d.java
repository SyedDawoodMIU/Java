import java.util.Stack;

interface Command {
    void execute();
    void undo();
}

class DepositCommand implements Command {
    private Account account;
    private double amount;

    public DepositCommand(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    public void execute() {
        account.deposit(amount);
    }

    public void undo() {
        account.withdraw(amount);
    }
}

class WithdrawCommand implements Command {
    private Account account;
    private double amount;

    public WithdrawCommand(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    public void execute() {
        account.withdraw(amount);
    }

    public void undo() {
        account.deposit(amount);
    }
}

class TransferCommand implements Command {
    private Account fromAccount;
    private Account toAccount;
    private double amount;

    public TransferCommand(Account fromAccount, Account toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public void execute() {
        fromAccount.transferFunds(toAccount, amount);
    }

    public void undo() {
        toAccount.transferFunds(fromAccount, amount);
    }
}

class Account {
    private double balance;

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void transferFunds(Account toAccount, double amount) {
        if (balance >= amount) {
            balance -= amount;
            toAccount.deposit(amount);
            System.out.println("Transferred: " + amount + " to account " + toAccount);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public double getBalance() {
        return balance;
    }
}

class TransactionManager {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public TransactionManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        } else {
            System.out.println("Nothing to undo");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        } else {
            System.out.println("Nothing to redo");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Account account1 = new Account();
        Account account2 = new Account();
        TransactionManager transactionManager = new TransactionManager();

        // Deposit example
        Command depositCommand = new DepositCommand(account1, 100.0);
        transactionManager.executeCommand(depositCommand);
        System.out.println("Account 1 Balance: " + account1.getBalance()); // Output: 100.0

        // Withdraw example
        Command withdrawCommand = new WithdrawCommand(account1, 50.0);
        transactionManager.executeCommand(withdrawCommand);
        System.out.println("Account 1 Balance: " + account1.getBalance()); // Output: 50.0

        // Transfer funds example
        Command transferCommand = new TransferCommand(account1, account2, 30.0);
        transactionManager.executeCommand(transferCommand);
        System.out.println("Account 1 Balance: " + account1.getBalance()); // Output: 20.0
        System.out.println("Account 2 Balance: " + account2.getBalance()); // Output: 30.0

        // Undo example
        transactionManager.undo();
        System.out.println("Account 1 Balance: " + account1.getBalance()); // Output: 50.0
        System.out.println("Account 2 Balance: " + account2.getBalance()); // Output: 0.0

        // Redo example
        transactionManager.redo();
        System.out.println("Account 1 Balance: " + account1.getBalance()); // Output: 20.0
        System.out.println("Account 2 Balance: " + account2.getBalance()); // Output: 30.0
    }
}
