package bank.service;

import bank.adapter.AccountAdapter;
import bank.dao.AccountDAO;
import bank.dao.IAccountDAO;
import bank.dto.AccountDTO;
import bank.dto.CustomerDTO;
import bank.dto.AccountEntryDTO;
import bank.domain.Account;
import bank.domain.Customer;

import java.util.Collection;
import java.util.List;

public class AccountService implements IAccountService {
    private IAccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountDTO createAccount(long accountNumber, String customerName) {
        Account account = new Account(accountNumber);
        Customer customer = new Customer(customerName);
        account.setCustomer(customer);
        accountDAO.saveAccount(account);
        return AccountAdapter.convertToDTO(account);
    }

    public void deposit(long accountNumber, double amount) {
        Account account = accountDAO.loadAccount(accountNumber);
        account.deposit(amount);
        accountDAO.updateAccount(account);
    }

    public AccountDTO getAccount(long accountNumber) {
        Account account = accountDAO.loadAccount(accountNumber);
        return AccountAdapter.convertToDTO(account);
    }

    public List<AccountDTO> getAllAccounts() {
        Collection<Account> accounts = accountDAO.getAccounts();
        return AccountAdapter.convertToDTOList(accounts);
    }

    public void withdraw(long accountNumber, double amount) {
        Account account = accountDAO.loadAccount(accountNumber);
        account.withdraw(amount);
        accountDAO.updateAccount(account);
    }

    public void transferFunds(long fromAccountNumber, long toAccountNumber, double amount, String description) {
        Account fromAccount = accountDAO.loadAccount(fromAccountNumber);
        Account toAccount = accountDAO.loadAccount(toAccountNumber);
        fromAccount.transferFunds(toAccount, amount, description);
        accountDAO.updateAccount(fromAccount);
        accountDAO.updateAccount(toAccount);
    }
}
