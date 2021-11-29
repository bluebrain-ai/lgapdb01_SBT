package com.bluescript.demo.jpa;

import java.sql.Timestamp;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluescript.demo.entity.PolicyEntity;

public interface ISelectPolicyLastChangedJpa extends JpaRepository<PolicyEntity, Integer> {

    @Query(value = "SELECT LASTCHANGED as caLastchanged FROM POLICY WHERE POLICYNUMBER = :db2PolicynumInt", nativeQuery = true)
    Timestamp getPolicyByDb2PolicynumInt(@Param("db2PolicynumInt") int db2PolicynumInt);
}
