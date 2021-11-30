package com.bluescript.demo.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

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
@Table(name = "MOTOR")
@Getter
@Setter
@Data
@RequiredArgsConstructor
// Schema : MOTOR
public class MotorEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "POLICYNUMBER")
    private int policyNumber;
    @Column(name = "MAKE")
    private String make;
    @Column(name = "MODEL")
    private String model;
    @Column(name = "VALUE")
    private int value;
    @Column(name = "REGNUMBER")
    private String regnumber;
    @Column(name = "COLOUR")
    private String colour;
    @Column(name = "CC")
    private int cc;
    @Column(name = "YEAROFMANUFACTURE")
    private Date yearofmanufacture;
    @Column(name = "PREMIUM")
    private int premium;
    @Column(name = "ACCIDENTS")
    private int accidents;

}
