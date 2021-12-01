package com.bluescript.demo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Component
@Entity
@Table(name = "COMMERCIAL")
@Getter
@Setter
@Data
@RequiredArgsConstructor
// Schema : COMMERCIAL
public class CommercialEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "POLICYNUMBER")
    private Integer policyNumber;
    @Column(name = "REQUESTDATE")
    private Timestamp requestDate;
    @Column(name = "STARTDATE")
    private Date startDate;
    @Column(name = "RENEWALDATE")
    private Date renewaldate;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "ZIPCODE")
    private String zipcode;
    @Column(name = "LATITUDEN")
    private String latitiden;
    @Column(name = "LONGITUDEW")
    private String longitudew;
    @Column(name = "CUSTOMER")
    private String customer;
    @Column(name = "PROPERTYTYPE")
    private String propertytype;
    @Column(name = "FIREPERIL")
    private Integer fireperil;
    @Column(name = "FIREPREMIUM")
    private Integer firepremium;
    @Column(name = "CRIMEPERIL")
    private Integer crimerperil;
    @Column(name = "CRIMEPREMIUM")
    private Integer crimepremium;
    @Column(name = "FLOODPERIL")
    private Integer floodperil;
    @Column(name = "FLOODPREMIUM")
    private Integer floodpremium;
    @Column(name = "WEATHERPERIL")
    private Integer weatherperil;
    @Column(name = "WEATHERPREMIUM")
    private Integer weatherpremium;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "REJECTIONREASON")
    private String rejectionreason;

}
