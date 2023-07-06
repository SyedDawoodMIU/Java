package bank.adapter;

import bank.domain.AccountEntry;
import bank.dto.AccountEntryDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountEntryAdapter {
    public static List<AccountEntryDTO> convertToDTOList(Collection<AccountEntry> entryList) {
        List<AccountEntryDTO> entryDTOList = new ArrayList<>();
        for (AccountEntry entry : entryList) {
            entryDTOList.add(convertToDTO(entry));
        }
        return entryDTOList;
    }

    public static AccountEntryDTO convertToDTO(AccountEntry entry) {
        AccountEntryDTO entryDTO = new AccountEntryDTO();
        entryDTO.setDate(entry.getDate());
        entryDTO.setAmount(entry.getAmount());
        entryDTO.setDescription(entry.getDescription());
        entryDTO.setFromAccountNumber(entry.getFromAccountNumber());
        entryDTO.setFromPersonName(entry.getFromPersonName());
        return entryDTO;
    }
}
