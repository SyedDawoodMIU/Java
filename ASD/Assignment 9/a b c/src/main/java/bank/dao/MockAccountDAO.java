package bank.dao;

import java.util.Collection;

import bank.domain.Account;

public class MockAccountDAO implements IAccountDAO {

    @Override
    public void saveAccount(Account account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAccount'");
    }

    @Override
    public void updateAccount(Account account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAccount'");
    }

    @Override
    public Account loadAccount(long accountnumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadAccount'");
    }

    @Override
    public Collection<Account> getAccounts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccounts'");
    }

}
