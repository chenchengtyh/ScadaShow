package com.nti56.scadashow.scadashow.bean;


import java.io.Serializable;

/**
 * Created by chencheng on 2017/7/28.
 */

public class Alarm implements Serializable {

    private String WAREHOUSENAME;
    private String TYPE;
    private String STATUS;
    private String ID;
    private String STATIONNO;
    private String ERRORCODE;
    private String ERRORSTRING;
    private String ERRORTIME;
    private String TASKNO;
    private String FROMSTA;
    private String TOSTA;
    private String LEVEL;
    private String USERNAME;
    private String METHOD;
    private String METHOD_HIS;
    private String UNKNOW_ERROR;
    private String STRINGENG;

    public void SetWAREHOUSENAME(String WAREHOUSENAME) {
        this.WAREHOUSENAME = WAREHOUSENAME;
    }

    public String GetWAREHOUSENAME() {
        return WAREHOUSENAME;
    }

    public void SetTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String GetTYPE() {
        return TYPE;
    }

    public void SetSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String GetSTATUS() {
        return STATUS;
    }

    public void SetID(String ID) {
        this.ID = ID;
    }

    public String GetID() {
        return ID;
    }

    public String GetSTATIONNO() {
        return STATIONNO;
    }

    public void SetSTATIONNO(String STATIONNO) {
        this.STATIONNO = STATIONNO;
    }

    public String GetERRORCODE() {
        return ERRORCODE;
    }

    public void SetERRORCODE(String ERRORCODE) {
        this.ERRORCODE = ERRORCODE;
    }

    public String GetERRORSTRING() {
        return ERRORSTRING;
    }

    public void SetERRORSTRING(String ERRORSTRING) {
        this.ERRORSTRING = ERRORSTRING;
    }

    public String GetERRORTIME() {
        return ERRORTIME;
    }

    public void SetERRORTIME(String ERRORTIME) {
        this.ERRORTIME = ERRORTIME;
    }

    public String GetTASKNO() {
        return TASKNO;
    }

    public void SetTASKNO(String TASKNO) {
        this.TASKNO = TASKNO;
    }

    public String GetFROMSTA() {
        return FROMSTA;
    }

    public void SetFROMSTA(String FROMSTA) {
        this.FROMSTA = FROMSTA;
    }

    public String GetTOSTA() {
        return TOSTA;
    }

    public void SetTOSTA(String TOSTA) {
        this.TOSTA = TOSTA;
    }

    public String GetLEVEL() {
        return LEVEL;
    }

    public void SetLEVEL(String LEVEL) {
        this.LEVEL = LEVEL;
    }

    public String GetUSERNAME() {
        return USERNAME;
    }

    public void SetUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String GetMETHOD() {
        return METHOD;
    }

    public void SetMETHOD(String METHOD) {
        this.METHOD = METHOD;
    }

    public String GetMETHOD_HIS() {
        return METHOD_HIS;
    }

    public void SetMETHOD_HIS(String METHOD_HIS) {
        this.METHOD_HIS = METHOD_HIS;
    }

    public String GetUNKNOW_ERROR() {
        return UNKNOW_ERROR;
    }

    public void SetUNKNOW_ERROR(String UNKNOW_ERROR) {
        this.UNKNOW_ERROR = UNKNOW_ERROR;
    }

    public String GetSTRINGENG() {
        return STRINGENG;
    }

    public void SetSTRINGENG(String STRINGENG) {
        this.STRINGENG = STRINGENG;

    }
}
