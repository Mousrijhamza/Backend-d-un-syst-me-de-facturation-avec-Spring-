package org.sid.facturationbackend.repositories;

import org.sid.facturationbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
  List<AccountOperation> findByEstateAccountId(String accountId);
//  Page<AccountOperation> findByEstateAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);
  @Query("SELECT o FROM AccountOperation o WHERE o.estateAccount.id like :accountId% ORDER BY o.operationDate DESC")
  Page<AccountOperation> findByEstateAccountIdOrderByOperationDateDesc(@Param("accountId") String accountId, Pageable pageable);

}
