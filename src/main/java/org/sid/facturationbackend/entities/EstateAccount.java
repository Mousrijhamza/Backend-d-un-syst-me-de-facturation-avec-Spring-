package org.sid.facturationbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.facturationbackend.enums.AccountStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "TYPE",length = 4)
@Data @NoArgsConstructor @AllArgsConstructor
public class EstateAccount {
    @Id
    private String id;
    private double montant;
    private Date createdAt;
    private String address;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne
    private RegieAggent regie;

    @OneToMany(mappedBy = "estateAccount",fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;

}
