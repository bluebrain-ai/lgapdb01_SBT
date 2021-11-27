package com.bluescript.demo.jpa;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluescript.demo.entity.EndowmentEntity;
import com.bluescript.demo.entity.EndowmentEntity;

public interface IInsertEndowment1Jpa extends JpaRepository<EndowmentEntity, String> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO ENDOWMENT ( POLICYNUMBER , WITHPROFITS , EQUITIES , MANAGEDFUND , FUNDNAME , TERM , SUMASSURED , LIFEASSURED ) VALUES ( :db2PolicynumInt , :caEWithProfits , :caEEquities , :caEManagedFund , :caEFundName , :db2ETermSint , :db2ESumassuredInt , :caELifeAssured )", nativeQuery = true)
    void insertEndowmentWithoutWSVaryField(@Param("db2PolicynumInt") int db2PolicynumInt,
            @Param("caEWithProfits") String caEWithProfits, @Param("caEEquities") String caEEquities,
            @Param("caEManagedFund") String caEManagedFund, @Param("caEFundName") String caEFundName,
            @Param("db2ETermSint") int db2ETermSint, @Param("db2ESumassuredInt") int db2ESumassuredInt,
            @Param("caELifeAssured") String caELifeAssured);
}
