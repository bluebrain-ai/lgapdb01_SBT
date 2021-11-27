package com.bluescript.demo.jpa;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluescript.demo.entity.HouseEntity;

public interface IInsertHouseJpa extends JpaRepository<HouseEntity, String> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO HOUSE ( POLICYNUMBER , PROPERTYTYPE , BEDROOMS , VALUE , HOUSENAME , HOUSENUMBER , POSTCODE ) VALUES ( :db2PolicynumInt , :caHPropertyType , :db2HBedroomsSint , :db2HValueInt , :caHHouseName , :caHHouseNumber , :caHPostcode )", nativeQuery = true)
    void insertHouseForDb2PolicynumIntAndCaHPropertyTypeAndDb2HBedroomsSint(
            @Param("db2PolicynumInt") int db2PolicynumInt, @Param("caHPropertyType") String caHPropertyType,
            @Param("db2HBedroomsSint") int db2HBedroomsSint, @Param("db2HValueInt") int db2HValueInt,
            @Param("caHHouseName") String caHHouseName, @Param("caHHouseNumber") String caHHouseNumber,
            @Param("caHPostcode") String caHPostcode);
}
