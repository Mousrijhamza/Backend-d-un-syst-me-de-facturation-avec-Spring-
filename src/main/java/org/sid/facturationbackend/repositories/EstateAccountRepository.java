package org.sid.facturationbackend.repositories;

import org.sid.facturationbackend.entities.EstateAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EstateAccountRepository extends JpaRepository<EstateAccount,String> {
}
