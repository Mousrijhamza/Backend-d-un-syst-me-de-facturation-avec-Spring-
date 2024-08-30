package org.sid.facturationbackend.dtos;

import lombok.Data;
import org.sid.facturationbackend.enums.AccountStatus;

import java.util.Date;

@Data
public class EstateAccountDTO {
    private String id;
    private double montant;
    private Date createdAt;
    private String address;
    private AccountStatus status;
    private RegieAggentDTO regieAggentDTO;

}
