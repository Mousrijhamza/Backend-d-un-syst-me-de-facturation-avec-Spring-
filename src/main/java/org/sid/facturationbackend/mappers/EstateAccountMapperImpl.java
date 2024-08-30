package org.sid.facturationbackend.mappers;

import org.sid.facturationbackend.dtos.AccountOperationDTO;
import org.sid.facturationbackend.dtos.EstateAccountDTO;
import org.sid.facturationbackend.dtos.RegieAggentDTO;
import org.sid.facturationbackend.entities.AccountOperation;
import org.sid.facturationbackend.entities.EstateAccount;
import org.sid.facturationbackend.entities.RegieAggent;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service
public class EstateAccountMapperImpl {
    public RegieAggentDTO fromregieAggent(RegieAggent regieAggent){
        RegieAggentDTO regieAggentDTO=new RegieAggentDTO();
        BeanUtils.copyProperties(regieAggent,regieAggentDTO);
        return  regieAggentDTO;
    }
    public RegieAggent fromregieAggentDTO(RegieAggentDTO regieAggentDTO){
        RegieAggent regieAggent=new RegieAggent();
        BeanUtils.copyProperties(regieAggentDTO,regieAggent);
        return  regieAggent;
    }

    public EstateAccountDTO fromestateAccount(EstateAccount estateAccount){
        EstateAccountDTO estateAccountDTO=new EstateAccountDTO();
        //ma9lobin li fi copyProperties
        BeanUtils.copyProperties(estateAccount,estateAccountDTO);
//        estateAccountDTO.setRegieAggentDTO(fromregieAggent(estateAccount.getRegie()));

        return estateAccountDTO;
    }

    public EstateAccount fromEstateAccountDTO(EstateAccountDTO estateAccountDTO){
        EstateAccount estateAccount=new EstateAccount();
        BeanUtils.copyProperties(estateAccountDTO,estateAccount);
//        estateAccount.setRegie(fromregieAggentDTO(estateAccountDTO.getRegieAggentDTO()));
        return estateAccount;
    }



    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }

}
