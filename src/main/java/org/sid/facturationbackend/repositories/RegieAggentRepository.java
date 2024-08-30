package org.sid.facturationbackend.repositories;


import org.sid.facturationbackend.entities.RegieAggent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegieAggentRepository extends JpaRepository<RegieAggent,Long> {
    @Query("select c from RegieAggent c where c.name like :kw")
    List<RegieAggent> searchRegieAggent(@Param("kw") String keyword);
}
