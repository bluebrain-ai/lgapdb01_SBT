package com.bluescript.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import java.util.*;

import org.springframework.stereotype.Component;

@Data
@Component

public class Db2InIntegers {
    private int db2CustomernumInt;
    private int db2BrokeridInt;
    private int db2PaymentInt;
    private int db2ETermSint;
    private int db2ESumassuredInt;
    private int db2HBedroomsSint;
    private int db2HValueInt;
    private int db2MValueInt;
    private int db2MCcSint;
    private int db2MPremiumInt;
    private int db2MAccidentsInt;
    private int db2BFireperilInt;
    private int db2BFirepremiumInt;
    private int db2BCrimeperilInt;
    private int db2BCrimepremiumInt;
    private int db2BFloodperilInt;
    private int db2BFloodpremiumInt;
    private int db2BWeatherperilInt;
    private int db2BWeatherpremiumInt;
    private int db2BStatusInt;
    private int db2CPolicynumInt;
    private int db2CNumInt = 0;
    private int db2CPaidInt;
    private int db2CValueInt;

}