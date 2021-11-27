package com.bluescript.demo.jpa;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluescript.demo.entity.CommercialEntity;

public interface IInsertCommercialJpa extends JpaRepository<CommercialEntity, String> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO COMMERCIAL ( POLICYNUMBER , REQUESTDATE , STARTDATE , RENEWALDATE , ADDRESS , ZIPCODE , LATITUDEN , LONGITUDEW , CUSTOMER , PROPERTYTYPE , FIREPERIL , FIREPREMIUM , CRIMEPERIL , CRIMEPREMIUM , FLOODPERIL , FLOODPREMIUM , WEATHERPERIL , WEATHERPREMIUM , STATUS , REJECTIONREASON ) VALUES ( :db2PolicynumInt , :caLastchanged , :caIssueDate , :caExpiryDate , :caBAddress , :caBPostcode , :caBLatitude , :caBLongitude , :caBCustomer , :caBProptype , :db2BFireperilInt , :db2BFirepremiumInt , :db2BCrimeperilInt , :db2BCrimepremiumInt , :db2BFloodperilInt , :db2BFloodpremiumInt , :db2BWeatherperilInt , :db2BWeatherpremiumInt , :db2BStatusInt , :caBRejectreason )", nativeQuery = true)
    void insertCommercialForDb2PolicynumIntAndCaLastchangedAndCaIssueDate(@Param("db2PolicynumInt") int db2PolicynumInt,
            @Param("caLastchanged") String caLastchanged, @Param("caIssueDate") String caIssueDate,
            @Param("caExpiryDate") String caExpiryDate, @Param("caBAddress") String caBAddress,
            @Param("caBPostcode") String caBPostcode, @Param("caBLatitude") String caBLatitude,
            @Param("caBLongitude") String caBLongitude, @Param("caBCustomer") String caBCustomer,
            @Param("caBProptype") String caBProptype, @Param("db2BFireperilInt") int db2BFireperilInt,
            @Param("db2BFirepremiumInt") int db2BFirepremiumInt, @Param("db2BCrimeperilInt") int db2BCrimeperilInt,
            @Param("db2BCrimepremiumInt") int db2BCrimepremiumInt, @Param("db2BFloodperilInt") int db2BFloodperilInt,
            @Param("db2BFloodpremiumInt") int db2BFloodpremiumInt,
            @Param("db2BWeatherperilInt") int db2BWeatherperilInt,
            @Param("db2BWeatherpremiumInt") int db2BWeatherpremiumInt, @Param("db2BStatusInt") int db2BStatusInt,
            @Param("caBRejectreason") String caBRejectreason);
}
