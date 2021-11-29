package com.bluescript.demo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name = "POLICY")
@Getter
@Setter
@Data
@RequiredArgsConstructor
// Schema : CUSTOMER
public class PolicyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(sequenceName = "SEQ_POLICY", allocationSize = 1, name = "CUST_SEQ")
    @Column(name = "POLICYNUMBER")
    private int policyNumber;
    @Column(name = "CUSTOMERNUMBER")
    private String customernumber;
    @Column(name = "ISSUEDATE")
    private String issuedate;
    @Column(name = "EXPIRYDATE")
    private String expirydate;
    @Column(name = "POLICYTYPE")
    private String policytype;
    @Column(name = "LASTCHANGED")
    private String lastchanged;
    @Column(name = "BROKERID")
    private String brokerid;
    @Column(name = "BROKERSREFERENCE")
    private String brokersreference;
    @Column(name = "PAYMENT")
    private String payment;
}
