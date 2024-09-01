package org.sid.facturationbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.facturationbackend.dtos.AccountHistoryDTO;
import org.sid.facturationbackend.dtos.AccountOperationDTO;
import org.sid.facturationbackend.dtos.EstateAccountDTO;
import org.sid.facturationbackend.dtos.RegieAggentDTO;
import org.sid.facturationbackend.entities.AccountOperation;
import org.sid.facturationbackend.entities.EstateAccount;
import org.sid.facturationbackend.entities.RegieAggent;
import org.sid.facturationbackend.enums.AccountStatus;
import org.sid.facturationbackend.enums.OperationType;
import org.sid.facturationbackend.exceptions.BankAccountNotFoundException;
import org.sid.facturationbackend.exceptions.CustomerNotFoundException;
import org.sid.facturationbackend.mappers.EstateAccountMapperImpl;
import org.sid.facturationbackend.repositories.AccountOperationRepository;
import org.sid.facturationbackend.repositories.EstateAccountRepository;
import org.sid.facturationbackend.repositories.RegieAggentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
//@Transactional
@AllArgsConstructor
@Slf4j
public class EstateAccountServiceImpl implements EstateAccountService {
    private RegieAggentRepository regieAggentRepository;
    private EstateAccountRepository estateAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private EstateAccountMapperImpl dtoMapper;

    @Override
    public RegieAggentDTO saveRegieAgent(RegieAggentDTO regieAggentDTO) {
        log.info("Saving new Aggent de Regie");
        RegieAggent regieAggent=dtoMapper.fromregieAggentDTO(regieAggentDTO);
        RegieAggent savedRegieAgent = regieAggentRepository.save(regieAggent);
        return dtoMapper.fromregieAggent(savedRegieAgent);
    }

    //Problemos
    //probleme trouver au niveau du mapper qui affect tous les value d'objet
    //il met null pour tous les values

    @Override
    public EstateAccountDTO saveEstateAccount(double iniTialmontant, String address) {
        EstateAccount estateAccount=new EstateAccount();
        estateAccount.setId( UUID.randomUUID().toString());
        estateAccount.setCreatedAt(new Date());
        estateAccount.setMontant(iniTialmontant);
        estateAccount.setStatus(AccountStatus.CREATED);
        estateAccount.setAddress(address);
        estateAccountRepository.save(estateAccount);
        EstateAccountDTO et =dtoMapper.fromestateAccount(estateAccount);
        System.out.println("1-2-"+et);
        return et;
    }
    @Override
    public EstateAccountDTO saveEstateAccount2(EstateAccount estateAccount){
        estateAccountRepository.save(estateAccount);
        return dtoMapper.fromestateAccount(estateAccount);
    }

    @Override
    public List<RegieAggentDTO> searchRegieAgent(String keyword) {
        List<RegieAggent> regieAggents = regieAggentRepository.findAll();
        List<RegieAggentDTO> regieAggentDTOS = regieAggents.stream()
                .map( (reg) -> dtoMapper.fromregieAggent(reg))
                .collect(Collectors.toList());

        return regieAggentDTOS;
    }

    @Override
    public List<EstateAccountDTO> searchEstateAccount(String keyword) {
        List<EstateAccount> estateAccountList = estateAccountRepository.findAll();
        List<EstateAccountDTO> estateAccountDTOList = estateAccountList.stream().map( (est)->
                dtoMapper.fromestateAccount(est))
                .collect(Collectors.toList());
        return estateAccountDTOList;

    }


    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        EstateAccount estAccount=estateAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("EstateAcocunt not found"));

//        if(bankAccount.getBalance()<amount)
//            throw new BalanceNotSufficientException("Balance not sufficient");

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.Eau);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setEstateAccount(estAccount);
        accountOperationRepository.save(accountOperation);
        estAccount.setMontant(estAccount.getMontant()-amount);

        System.out.println("credit ajouter une couento");
        estateAccountRepository.save(estAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        EstateAccount estAccount=estateAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("EstateAcocunt not found"));

//        if(bankAccount.getBalance()<amount)
//            throw new BalanceNotSufficientException("Balance not sufficient");

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.Électricité);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setEstateAccount(estAccount);
        accountOperationRepository.save(accountOperation);
        estAccount.setMontant(estAccount.getMontant()+amount);

        System.out.println("credit ajouter une couento ");
        estateAccountRepository.save(estAccount);
    }


    @Override
    public List<EstateAccountDTO> listEstateAccount() {
        List<EstateAccount> bankAccounts = estateAccountRepository.findAll();
        System.out.println("-bankos-");
        List<EstateAccountDTO> bankAccountDTOS = bankAccounts.stream()
                .map(est -> dtoMapper.fromestateAccount(est))
                .collect(Collectors.toList());
        System.out.println(bankAccountDTOS);
        return bankAccountDTOS;
    }


    @Override
    public RegieAggentDTO getRegie(Long regieId) throws CustomerNotFoundException {
        RegieAggent regieAggent = regieAggentRepository.findById(regieId).
                orElseThrow(() -> new CustomerNotFoundException("RegieAggent Not found"));
        return dtoMapper.fromregieAggent(regieAggent);
    }

    @Override
    public RegieAggentDTO updateRegie(RegieAggentDTO regieAggentDTO) {
        log.info("Updating regie agent ");
        RegieAggent regieAggent=dtoMapper.fromregieAggentDTO(regieAggentDTO);
        RegieAggent regieAggent1=regieAggentRepository.save(regieAggent);
        return dtoMapper.fromregieAggent(regieAggent1);
    }

    @Override
    public void deleteRegieAgent(Long regieId) throws CustomerNotFoundException {
        regieAggentRepository.deleteById(regieId);
    }

    @Override
    public void deleteEstateAccount(String id) {
        estateAccountRepository.deleteById(id);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByEstateAccountId(accountId);
        return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        EstateAccount bankAccount=estateAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByEstateAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(Long.valueOf(bankAccount.getId()));
        accountHistoryDTO.setBalance(bankAccount.getMontant());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }


    @Override
    public List<RegieAggentDTO> listRegieAggent() {
        List<RegieAggent> customers=regieAggentRepository.findAll();
        List<RegieAggentDTO> regieAggentDTOS = customers.stream().map(cust -> dtoMapper.fromregieAggent(cust)).collect(Collectors.toList());
        return regieAggentDTOS;

    }

    @Override
    public EstateAccountDTO getEstateAccountDTO(String EstateId) throws BankAccountNotFoundException {
        EstateAccount estAccount=estateAccountRepository.findById(EstateId)
                .orElseThrow(()->new BankAccountNotFoundException("Estate not found"));

        return dtoMapper.fromestateAccount(estAccount);

    }
}
