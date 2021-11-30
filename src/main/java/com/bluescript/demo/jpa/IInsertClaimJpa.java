package com.bluescript.demo.jpa;

import java.sql.Date;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluescript.demo.entity.ClaimEntity;

public interface IInsertClaimJpa extends JpaRepository<ClaimEntity, String> {
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO CLAIM ( CLAIMNUMBER , POLICYNUMBER , CLAIMDATE , PAID , VALUE , CAUSE , OBSERVATIONS ) VALUES ( :db2CNumInt , :db2CPolicynumInt , :caCDate , :db2CPaidInt , :db2CValueInt , :caCCause , :caCObservations )", nativeQuery = true)
    void insertClaimForDb2CNumIntAndDb2CPolicynumIntAndCaCDate(@Param("db2CNumInt") int db2CNumInt,
            @Param("db2CPolicynumInt") int db2CPolicynumInt, @Param("caCDate") Date caCDate,
            @Param("db2CPaidInt") int db2CPaidInt, @Param("db2CValueInt") int db2CValueInt,
            @Param("caCCause") String caCCause, @Param("caCObservations") String caCObservations);
}
