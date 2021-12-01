package com.bluescript.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Data
@Component

public class CaPolicyCommon {
    private Date caIssueDate;
    private Date caExpiryDate;
    private String caLastchanged;
    private long caBrokerid;
    private String caBrokersref;
    private int caPayment;

    public String toFixedWidthString() {
        return caBrokersref + caIssueDate + caExpiryDate + caLastchanged + caBrokerid + caPayment;
    }

}