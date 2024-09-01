package org.sid.facturationbackend.services;

import org.sid.facturationbackend.dtos.*;
import org.sid.facturationbackend.entities.EstateAccount;
import org.sid.facturationbackend.exceptions.BankAccountNotFoundException;
import org.sid.facturationbackend.exceptions.CustomerNotFoundException;

import java.util.List;
public interface EstateAccountService {
    RegieAggentDTO saveRegieAgent(RegieAggentDTO regieAggentDTO);
    EstateAccountDTO saveEstateAccount(double montant, String address);
    EstateAccountDTO saveEstateAccount2(EstateAccount estateAccount);

    List<RegieAggentDTO> listRegieAggent();
    EstateAccountDTO getEstateAccountDTO(String EstateId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;


    List<EstateAccountDTO> listEstateAccount();

    RegieAggentDTO getRegie(Long regieId) throws CustomerNotFoundException;

    RegieAggentDTO updateRegie(RegieAggentDTO regieAggentDTO);

    void deleteRegieAgent(Long regieId) throws CustomerNotFoundException;
    void deleteEstateAccount(String id) ;

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<RegieAggentDTO> searchRegieAgent(String keyword);

    List<EstateAccountDTO> searchEstateAccount(String keyword);
}
