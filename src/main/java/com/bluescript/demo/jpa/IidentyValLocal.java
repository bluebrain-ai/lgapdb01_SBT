package com.bluescript.demo.jpa;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

@Repository
public interface IidentyValLocal extends JpaRepository<IdentityFuncEntity, Double> {

    // @Query(value = "SELECT IDENTITY_VAL_LOCAL() FROM DUAL", nativeQuery = true)
    @Query(value = "SELECT RAND() AS RANDOM_NUMBER FROM DUAL", nativeQuery = true)
    double getDb2PolicynumInt();
}
