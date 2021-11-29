package com.bluescript.demo.entity;

import java.io.Serializable;
import java.sql.Date;

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

@Component
@Entity
@Table(name = "CLAIM")

@Data
@RequiredArgsConstructor
// Schema : CLAIM -- sequence required
public class ClaimEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CLAIMNUMBER")
    private int claimNumber;
    @Column(name = "POLICYNUMBER")
    private int policynumber;
    @Column(name = "CLAIMDATE")
    private Date claimdate;
    @Column(name = "PAID")
    private int paid;
    @Column(name = "VALUE")
    private int value;
    @Column(name = "CAUSE")
    private String cause;
    @Column(name = "OBSERVATIONS")
    private String observations;
}
