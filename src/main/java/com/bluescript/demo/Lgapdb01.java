package com.bluescript.demo;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import io.swagger.annotations.ApiResponses;

import com.bluescript.demo.jpa.IInsertPolicyJpa;
import com.bluescript.demo.jpa.ISelectPolicyLastChangedJpa;
import com.bluescript.demo.jpa.IidentyValLocalFunc;
import com.bluescript.demo.jpa.IInsertEndowmentJpa;
import com.bluescript.demo.jpa.IInsertEndowment1Jpa;
import com.bluescript.demo.jpa.IInsertHouseJpa;
import com.bluescript.demo.jpa.IInsertMotorJpa;
import com.bluescript.demo.jpa.IInsertCommercialJpa;
import com.bluescript.demo.jpa.IInsertClaimJpa;
import com.bluescript.demo.model.WsHeader;
import com.bluescript.demo.model.ErrorMsg;
import com.bluescript.demo.model.EmVariable;
import com.bluescript.demo.model.WsVaryField;
import com.bluescript.demo.model.Db2InIntegers;
import com.bluescript.demo.model.Dfhcommarea;

@Getter
@Setter
@RequiredArgsConstructor
@Log4j2
@Component

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
        @io.swagger.annotations.ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
        @io.swagger.annotations.ApiResponse(code = 500, message = "The server/Application is down. Please contact support team.") })

public class Lgapdb01 {

    @Autowired
    private WsHeader wsHeader;
    @Autowired
    private ErrorMsg errorMsg;
    @Autowired
    private EmVariable emVariable;
    @Autowired
    private WsVaryField wsVaryField;
    @Autowired
    private Db2InIntegers db2InIntegers;
    @Autowired
    private Dfhcommarea dfhcommarea;
    private String time1;
    private String date1;
    private String caData;
    private int wsCaHeaderLen = 0;
    private int wsRequiredCaLen = 0;
    private int db2PolicynumInt = 0;
    private static final int wsFullEndowLen = 124;
    private static final int wsFullHouseLen = 130;
    private static final int wsFullMotorLen = 137;
    private int eibcalen;
    private String wsDate;

    @Autowired
    private ISelectPolicyLastChangedJpa selectPolicyLastDate;
    @Autowired
    private IInsertPolicyJpa InsertPolicyJpa;
    @Autowired
    private IInsertEndowmentJpa InsertEndowmentJpa;
    @Autowired
    private IInsertMotorJpa InsertMotorJpa;
    @Autowired
    private IInsertEndowment1Jpa InsertEndowment1Jpa;
    @Autowired
    private IInsertHouseJpa InsertHouseJpa;
    @Autowired
    private IInsertClaimJpa InsertClaimJpa;
    @Autowired
    private IInsertCommercialJpa InsertCommercialJpa;

    private String db2Policytype;
    @Value("${api.lgapvs01.host}")
    private String lgapvs01_host;
    @Value("${api.lgapvs01.URI}")
    private String lgapvs01_URI;
    @Value("${api.LGSTSQ.host}")
    private String LGSTSQ_HOST;
    @Value("${api.LGSTSQ.URI}")
    private String LGSTSQ_URI;
    private String caErrorMsg;

    @Autowired
    private IidentyValLocalFunc identyValLocal;

    @PostMapping("/lgapdb01")
    public ResponseEntity<Dfhcommarea> mainLine(@RequestBody Dfhcommarea payload) {
        // if( eibcalen == 0 )
        // {
        // errorMsg.setEmVariable(" NO COMMAREA RECEIVED"); writeErrorMessage();
        // log.error("Error code :", LGCA);
        // throw new LGCAException("LGCA");

        // }
        BeanUtils.copyProperties(payload, dfhcommarea);
        wsRequiredCaLen = wsCaHeaderLen + wsRequiredCaLen;

        switch (dfhcommarea.getCaRequestId()) {
        case "01AEND":
            wsRequiredCaLen = wsFullEndowLen + wsRequiredCaLen;
            break;
        case "01AHOU":
            wsRequiredCaLen = wsFullHouseLen + wsRequiredCaLen;
            break;
        case "01AMOT":
            wsRequiredCaLen = wsFullMotorLen + wsRequiredCaLen;
            db2Policytype = "M";
            break;
        case "01ACOM":
            db2Policytype = "C";
            break;
        case "01ACLM":
            db2Policytype = "X";
            break;
        default:
            dfhcommarea.setCaReturnCode(99);
            /* return */
            break;

        }
        if (dfhcommarea.getCaRequestId() != "01ACLM") {
            insertPolicy();
        }
        switch (dfhcommarea.getCaRequestId()) {
        case "01AEND":
            insertEndow();
            break;
        case "01AHOU":
            insertHouse();
            break;
        case "01AMOT":
            insertMotor();
            break;
        case "01ACOM":
            insertCommercial();
            break;
        case "01ACLM":
            insertClaim();
            break;
        default:
            dfhcommarea.setCaReturnCode(99);
        }

        try {
            WebClient webclientBuilder = WebClient.create(lgapvs01_host);
            Mono<Dfhcommarea> lgapvs01Resp = webclientBuilder.post().uri(lgapvs01_URI)
                    .body(Mono.just(dfhcommarea), Dfhcommarea.class).retrieve().bodyToMono(Dfhcommarea.class);// .timeout(Duration.ofMillis(10_000));
            dfhcommarea = lgapvs01Resp.block();
        } catch (Exception e) {
            log.error(e);
        }

        return new ResponseEntity<>(dfhcommarea, HttpStatus.OK);
    }

    public void insertPolicy() {
        log.warn("MethodinsertPolicystarted..");

        db2InIntegers.setDb2BrokeridInt((int) dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaBrokerid());
        db2InIntegers.setDb2PaymentInt(dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaPayment());
        emVariable.setEmSqlreq(" INSERT POLICY");
        try {
            InsertPolicyJpa.insertPolicyForDb2CustomernumIntAndCaIssueDateAndCaExpiryDate(
                    db2InIntegers.getDb2CustomernumInt(),
                    dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaIssueDate(),
                    dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaExpiryDate(), db2Policytype,
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), db2InIntegers.getDb2BrokeridInt(),
                    dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaBrokersref(),
                    db2InIntegers.getDb2PaymentInt());
          
        } catch (ConstraintViolationException ex) {
            log.error(ex);
            dfhcommarea.setCaReturnCode(70);
            // writeErrorMessage();
        } catch (Exception e) {
            log.error(e);
            dfhcommarea.setCaReturnCode(90);
            // writeErrorMessage();
        }
        /*
         * EXEC SQL SET :DB2-POLICYNUM-INT = IDENTITY_VAL_LOCAL() END-EXEC
         */

        db2PolicynumInt = identyValLocal.getDb2PolicynumInt();

        log.warn("db2PolicynumInt:" + db2PolicynumInt);
        dfhcommarea.getCaPolicyRequest().setCaPolicyNum(db2PolicynumInt);
        emVariable.setEmPolNum((int) dfhcommarea.getCaPolicyRequest().getCaPolicyNum());

        try {
            Timestamp lastChanged = selectPolicyLastDate.getPolicyByDb2PolicynumInt(db2PolicynumInt);
            dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().setCaLastchanged(lastChanged);

        } catch (Exception e) {
            log.error(e);
        }

        log.debug("Method insertPolicy completed..");
    }

    // @Transactional
    public void insertEndow() {
        log.debug("MethodinsertEndowstarted..");
        db2InIntegers.setDb2ETermSint(dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaETerm());
        db2InIntegers.setDb2ESumassuredInt(dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaESumAssured());

        emVariable.setEmSqlreq(" INSERT ENDOW ");
        wsVaryField.setWsVaryLen(wsRequiredCaLen - eibcalen);

        try {

            if (wsVaryField.getWsVaryLen() < 0) {

                wsVaryField.setWsVaryChar(dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEPaddingData());

                InsertEndowmentJpa.insertEndowmentForDb2PolicynumIntAndCaEWithProfitsAndCaEEquities(db2PolicynumInt,
                        dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEWithProfits(),
                        dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEEquities(),
                        dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEManagedFund(),
                        dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEFundName(),
                        db2InIntegers.getDb2ETermSint(), db2InIntegers.getDb2ESumassuredInt(),
                        dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaELifeAssured(), wsVaryField.toString());

            } else {

                InsertEndowment1Jpa.insertEndowmentWithoutWSVaryField(db2PolicynumInt,
                        dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEWithProfits(),
                        dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEEquities(),
                        dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEManagedFund(),
                        dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaEFundName(),
                        db2InIntegers.getDb2ETermSint(), db2InIntegers.getDb2ESumassuredInt(),
                        dfhcommarea.getCaPolicyRequest().getCaEndowment().getCaELifeAssured());
            }
        }

        catch (Exception e) {
            writeErrorMessage();
            log.error("Error code : LGSQ" + e);
            dfhcommarea.setCaReturnCode(90);
            throw new LGSQException("LGSQ");

        }

        log.debug("Method insertEndow completed..");
    }

    @Transactional
    public void insertHouse() {
        log.debug("MethodinsertHousestarted..");
        db2InIntegers.setDb2HValueInt(dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHValue());
        db2InIntegers.setDb2HBedroomsSint(dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHBedrooms());
        emVariable.setEmSqlreq(" INSERT HOUSE ");
        log.warn("db2PolicynumInt:" + db2PolicynumInt);
        try {
            InsertHouseJpa.insertHouseForDb2PolicynumIntAndCaHPropertyTypeAndDb2HBedroomsSint(db2PolicynumInt,
                    dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHPropertyType(),
                    db2InIntegers.getDb2HBedroomsSint(), db2InIntegers.getDb2HValueInt(),
                    dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHHouseName(),
                    dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHHouseNumber(),
                    dfhcommarea.getCaPolicyRequest().getCaHouse().getCaHPostcode());

        } catch (Exception e) {
            dfhcommarea.setCaReturnCode(90);
            writeErrorMessage();
            log.error("Error code :, LGSQ");
            throw new LGSQException("LGSQ");

        }

        log.debug("Method insertHouse completed..");
    }

    @Transactional
    public void insertMotor() {
        log.debug("MethodinsertMotorstarted..");
        db2InIntegers.setDb2MValueInt(dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMValue());
        db2InIntegers.setDb2MCcSint(dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMCc());
        db2InIntegers.setDb2MPremiumInt(dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMPremium());
        db2InIntegers.setDb2MAccidentsInt(dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMAccidents());

        emVariable.setEmSqlreq(" INSERT MOTOR ");

        try {
            InsertMotorJpa.insertMotorForDb2PolicynumIntAndCaMMakeAndCaMModel(db2PolicynumInt,
                    dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMMake(),
                    dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMModel(), db2InIntegers.getDb2MValueInt(),
                    dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMRegnumber(),
                    dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMColour(), db2InIntegers.getDb2MCcSint(),
                    dfhcommarea.getCaPolicyRequest().getCaMotor().getCaMManufactured(),
                    db2InIntegers.getDb2MPremiumInt(), db2InIntegers.getDb2MAccidentsInt());

        } catch (Exception e) {
            dfhcommarea.setCaReturnCode(90);
            writeErrorMessage();
            log.error("Error code :, LGSQ");
            throw new LGSQException("LGSQ");

        }

        log.debug("Method insertMotor completed..");
    }

    @Transactional
    public void insertCommercial() {
        log.debug("MethodinsertCommercialstarted..");
        emVariable.setEmSqlreq(" INSERT COMMER");

        db2InIntegers.setDb2BFireperilInt(dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBFireperil());
        db2InIntegers.setDb2BFirepremiumInt(dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBFirepremium());
        db2InIntegers.setDb2BCrimeperilInt(dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBCrimeperil());
        db2InIntegers.setDb2BCrimepremiumInt(dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBCrimepremium());
        db2InIntegers.setDb2BFloodperilInt(dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBFloodperil());
        db2InIntegers.setDb2BFloodpremiumInt(dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBFloodpremium());
        db2InIntegers.setDb2BWeatherperilInt(dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBWeatherperil());
        db2InIntegers
                .setDb2BWeatherpremiumInt(dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBWeatherpremium());
        db2InIntegers.setDb2BStatusInt(db2InIntegers.getDb2BStatusInt());
        try {

            InsertCommercialJpa.insertCommercialForDb2PolicynumIntAndCaLastchangedAndCaIssueDate(db2PolicynumInt,
                    dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaLastchanged(),
                    dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaIssueDate(),
                    dfhcommarea.getCaPolicyRequest().getCaPolicyCommon().getCaExpiryDate(),
                    dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBAddress(),
                    dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBPostcode(),
                    dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBLatitude(),
                    dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBLongitude(),
                    dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBCustomer(),
                    dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBProptype(),
                    db2InIntegers.getDb2BFireperilInt(), db2InIntegers.getDb2BFirepremiumInt(),
                    db2InIntegers.getDb2BCrimeperilInt(), db2InIntegers.getDb2BCrimepremiumInt(),
                    db2InIntegers.getDb2BFloodperilInt(), db2InIntegers.getDb2BFloodpremiumInt(),
                    db2InIntegers.getDb2BWeatherperilInt(), db2InIntegers.getDb2BWeatherpremiumInt(),
                    db2InIntegers.getDb2BStatusInt(),
                    dfhcommarea.getCaPolicyRequest().getCaCommercial().getCaBRejectreason());
        } catch (Exception e) {
            dfhcommarea.setCaReturnCode(90);
            writeErrorMessage();
            log.error("Error code :, LGSQ");
            throw new LGSQException("LGSQ");
            /* return */

        }

        log.debug("Method insertCommercial completed..");
    }

    @Transactional
    public void insertClaim() {
        log.debug("MethodinsertClaimstarted..");
        emVariable.setEmSqlreq(" INSERT CLAIM");
        try {
            InsertClaimJpa.insertClaimForDb2CNumIntAndDb2CPolicynumIntAndCaCDate(db2InIntegers.getDb2CNumInt(),
                    db2InIntegers.getDb2CPolicynumInt(), dfhcommarea.getCaPolicyRequest().getCaClaim().getCaCDate(),
                    db2InIntegers.getDb2CPaidInt(), db2InIntegers.getDb2CValueInt(),
                    dfhcommarea.getCaPolicyRequest().getCaClaim().getCaCCause(),
                    dfhcommarea.getCaPolicyRequest().getCaClaim().getCaCObservations());
            dfhcommarea.getCaPolicyRequest().getCaClaim().setCaCNum(db2InIntegers.getDb2CNumInt());
        } catch (Exception e) {
            dfhcommarea.setCaReturnCode(90);
            writeErrorMessage();
            log.error("Error code :, LGSQ");
            throw new LGSQException("LGSQ");
            /* return */
        }

        log.debug("Method insertClaim completed..");
    }

    public void writeErrorMessage() {
        log.debug("MethodwriteErrorMessagestarted..");
        String wsAbstime = LocalTime.now().toString();
        String wsDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // String wsDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); //yyyyMMdd
        String wsTime = LocalTime.now().toString();
        errorMsg.setEmDate(wsDate.substring(0, 8));
        errorMsg.setEmTime(wsTime.substring(0, 6));
        WebClient webclientBuilder = WebClient.create(LGSTSQ_HOST);
        try {
            Mono<ErrorMsg> lgstsqResp = webclientBuilder.post().uri(LGSTSQ_URI)
                    .body(Mono.just(errorMsg), ErrorMsg.class).retrieve().bodyToMono(ErrorMsg.class);// .timeout(Duration.ofMillis(10_000));
            errorMsg = lgstsqResp.block();
        } catch (Exception e) {
            log.error(e);
        }
        if (eibcalen > 0) {
            if (eibcalen < 91) {
                try {
                    Mono<ErrorMsg> lgstsqResp = webclientBuilder.post().uri(LGSTSQ_URI)
                            .body(Mono.just(errorMsg), ErrorMsg.class).retrieve().bodyToMono(ErrorMsg.class);// .timeout(Duration.ofMillis(10_000));
                    errorMsg = lgstsqResp.block();
                } catch (Exception e) {
                    log.error(e);
                }

            } else {
                try {
                    Mono<String> lgstsqResp = webclientBuilder.post().uri(LGSTSQ_URI)
                            .body(Mono.just(caErrorMsg), String.class).retrieve().bodyToMono(String.class);// .timeout(Duration.ofMillis(10_000));
                    caErrorMsg = lgstsqResp.block();
                } catch (Exception e) {
                    log.error(e);
                }

            }

        }

        log.debug("Method writeErrorMessage completed..");

    }

    /* End of program */
}