package org.sid.facturationbackend.repositories;

import org.sid.facturationbackend.entities.EstateAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface EstateAccountRepository extends JpaRepository<EstateAccount,String> {
    @Query("SELECT e FROM EstateAccount e WHERE e.id LIKE :x%")
    EstateAccount findByIdStartingWith(@Param("x") String keyword);
}
