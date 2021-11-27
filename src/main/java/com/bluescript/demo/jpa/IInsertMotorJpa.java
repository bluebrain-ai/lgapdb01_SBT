package com.bluescript.demo.jpa;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluescript.demo.entity.MotorEntity;

public interface IInsertMotorJpa extends JpaRepository<MotorEntity, String> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO MOTOR ( POLICYNUMBER , MAKE , MODEL , VALUE , REGNUMBER , COLOUR , CC , YEAROFMANUFACTURE , PREMIUM , ACCIDENTS ) VALUES ( :db2PolicynumInt , :caMMake , :caMModel , :db2MValueInt , :caMRegnumber , :caMColour , :db2MCcSint , :caMManufactured , :db2MPremiumInt , :db2MAccidentsInt )", nativeQuery = true)
    void insertMotorForDb2PolicynumIntAndCaMMakeAndCaMModel(@Param("db2PolicynumInt") int db2PolicynumInt,
            @Param("caMMake") String caMMake, @Param("caMModel") String caMModel,
            @Param("db2MValueInt") int db2MValueInt, @Param("caMRegnumber") String caMRegnumber,
            @Param("caMColour") String caMColour, @Param("db2MCcSint") int db2MCcSint,
            @Param("caMManufactured") String caMManufactured, @Param("db2MPremiumInt") int db2MPremiumInt,
            @Param("db2MAccidentsInt") int db2MAccidentsInt);
}
