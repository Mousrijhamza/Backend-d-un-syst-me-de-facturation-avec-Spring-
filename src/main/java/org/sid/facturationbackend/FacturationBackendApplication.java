package org.sid.facturationbackend;


import org.sid.facturationbackend.dtos.EstateAccountDTO;
import org.sid.facturationbackend.dtos.RegieAggentDTO;
import org.sid.facturationbackend.entities.*;
import org.sid.facturationbackend.enums.AccountStatus;
import org.sid.facturationbackend.enums.OperationType;
import org.sid.facturationbackend.exceptions.CustomerNotFoundException;
import org.sid.facturationbackend.repositories.AccountOperationRepository;
import org.sid.facturationbackend.repositories.EstateAccountRepository;
import org.sid.facturationbackend.repositories.RegieAggentRepository;
import org.sid.facturationbackend.services.EstateAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class FacturationBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacturationBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(EstateAccountService bankAccountService
    ){
        return args -> {
           Stream.of("Hassan","Imane","Mohamed").forEach(name->{
               RegieAggentDTO customer=new RegieAggentDTO();
               customer.setName(name);
               customer.setEmail(name+"@gmail.com");
               bankAccountService.saveRegieAgent(customer);

           });
           bankAccountService.listRegieAggent().forEach(customer->{
               try {
                   bankAccountService.saveEstateAccount(Math.random()*90000, "hay1");
                   bankAccountService.saveEstateAccount(Math.random()*120000, "hay2");


               } catch (Exception e) {
                   e.printStackTrace();
               }
           });
            List<EstateAccountDTO> bankAccounts = bankAccountService.listEstateAccount();
            for (EstateAccountDTO bankAccount:bankAccounts){
                for (int i = 0; i <10 ; i++) {
                    if (bankAccount.getId()!=null){
                        String accountId =((EstateAccountDTO) bankAccount).getId();
                        bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
                        bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                    }
                }
            }
        };
    }
//    @Bean
    CommandLineRunner start(RegieAggentRepository regieAggentRepository,
                            EstateAccountRepository estateAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                RegieAggent regieAggent=new RegieAggent();
                regieAggent.setName(name);
                regieAggent.setEmail(name+"@gmail.com");
                regieAggentRepository.save(regieAggent);
            });
            regieAggentRepository.findAll().forEach(cust->{
                EstateAccount currentAccount=new EstateAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setMontant(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                estateAccountRepository.save(currentAccount);

            });
            estateAccountRepository.findAll().forEach(acc->{
                for (int i = 0; i <10 ; i++) {
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.Eau:
                            OperationType.Électricité);
                    accountOperation.setEstateAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }

            });
        };

    }

}
