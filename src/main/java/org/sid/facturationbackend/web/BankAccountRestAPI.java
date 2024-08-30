package org.sid.facturationbackend.web;

import org.sid.facturationbackend.dtos.*;
import org.sid.facturationbackend.entities.EstateAccount;
import org.sid.facturationbackend.exceptions.BalanceNotSufficientException;
import org.sid.facturationbackend.exceptions.BankAccountNotFoundException;

import org.sid.facturationbackend.services.EstateAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestAPI {
    private EstateAccountService bankAccountService;

    public BankAccountRestAPI(EstateAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/Estaccounts")
    public EstateAccountDTO addEstateAccount(@RequestBody EstateAccountDTO estAccountDTO){
        return bankAccountService.saveEstateAccount(estAccountDTO.getMontant(), estAccountDTO.getAddress());
    }

    @GetMapping("/Estaccounts/{accountId}")
    public EstateAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getEstateAccountDTO(accountId);
    }
    @GetMapping("/Estaccounts")
    public List<EstateAccountDTO> listAccounts(){
        return bankAccountService.listEstateAccount();
    }
    @GetMapping("/Estaccounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/Estaccounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/Estaccounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/Estaccounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }

}
