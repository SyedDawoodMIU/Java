package bank.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bank.domain.Account;
import bank.dto.AccountDTO;

public class AccountAdapter {

    public static List<AccountDTO> convertToDTOList(Collection<Account> accountList) {
        List<AccountDTO> accountDTOs = new ArrayList<>();
        for (Account entry : accountList) {
            accountDTOs.add(convertToDTO(entry));
        }
        return accountDTOs;
    }

    public static AccountDTO convertToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(account.getAccountnumber());
        accountDTO.setEntryList(AccountEntryAdapter.convertToDTOList(account.getEntryList()));
        accountDTO.setCustomer(CustomerAdapter.convertToDTO(account.getCustomer()));
        return accountDTO;
    }
}
