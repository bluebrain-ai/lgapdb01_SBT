package com.bluescript.demo.jpa;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import com.bluescript.demo.entity.PolicyEntity;

public interface IInsertPolicyJpa extends JpaRepository<PolicyEntity, Integer> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO POLICY ( POLICYNUMBER , CUSTOMERNUMBER , ISSUEDATE , EXPIRYDATE , POLICYTYPE , LASTCHANGED , BROKERID , BROKERSREFERENCE , PAYMENT ) VALUES ( DEFAULT , :db2CustomernumInt , :caIssueDate , :caExpiryDate , :db2Policytype , CURRENT TIMESTAMP , :db2BrokeridInt , :caBrokersref , :db2PaymentInt )", nativeQuery = true)
    void insertPolicyForDb2CustomernumIntAndCaIssueDateAndCaExpiryDate(
            @Param("db2CustomernumInt") int db2CustomernumInt, @Param("caIssueDate") String caIssueDate,
            @Param("caExpiryDate") String caExpiryDate, @Param("db2Policytype") String db2Policytype,
            @Param("db2BrokeridInt") int db2BrokeridInt, @Param("caBrokersref") String caBrokersref,
            @Param("db2PaymentInt") int db2PaymentInt);
}
