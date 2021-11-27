package com.bluescript.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import java.util.*;

import org.springframework.stereotype.Component;

@Data
@Component

public class EmVariable {
    private static final String emCusNumFiller = "CNUM=";
    private String emCusnum;
    private static final String emPolNumFiller = "PNUM=";
    private int emPolNum;
    private String emSqlreq;
    private static final String emSqlRcFiller = "SQLCODE=";
    private int emSqlRc;

    public String toFixedWidthString() {
        return emCusnum + emSqlreq + emSqlRc;
    }

}