package com.nti56.scadashow.scadashow.bean;

import java.io.Serializable;

/**
 * Created by chencheng on 2017/8/24.
 */

public class OrderInfo implements Serializable {

    private String WAREHOUSEID;  //仓库Id
    private String EQUIPMENTCODE; //设备编码
    private String EQUIPMENTTYPE;//设备类型
    private String ORDERNUMBER;//维保工单号
    private String FIXPARTID;//维保部件id
    private String ODERDESCRIPTION;//维保内容
    private String CREATEUSER;//上传人
    private String AUDITPERSON;//审核人
    private String EXECUTEPERSON;//执行人
    private String ORDERTYPE;//工单类型（人工/自动）
    private String CREATEDATE;//创建时间
    private String AUDITTIME;//审核时间（下达时间）
    private String ENDTIME;//工单完成时间
    private String ORDERSTATUS;//工单状态（新建,下达,已完成）
    private String ORDERRECODER;//维保记录
    private String ALLPERSON;

    public void SetWAREHOUSEID(String WAREHOUSEID) {
        this.WAREHOUSEID = WAREHOUSEID;
    }

    public String GetWAREHOUSEID() {
        return WAREHOUSEID;
    }

    public void SetEQUIPMENTCODE(String EQUIPMENTCODE) {
        this.EQUIPMENTCODE = EQUIPMENTCODE;
    }

    public String GetEQUIPMENTCODE() {
        return EQUIPMENTCODE;
    }

    public void SetEQUIPMENTTYPE(String EQUIPMENTTYPE) {
        this.EQUIPMENTTYPE = EQUIPMENTTYPE;
    }

    public String GetEQUIPMENTTYPE() {
        return EQUIPMENTTYPE;
    }

    public void SetORDERNUMBER(String ORDERNUMBER) {
        this.ORDERNUMBER = ORDERNUMBER;
    }

    public String GetORDERNUMBER() {
        return ORDERNUMBER;
    }

    public void SetFIXPARTID(String FIXPARTID) {
        this.FIXPARTID = FIXPARTID;
    }

    public String GetFIXPARTID() {
        return FIXPARTID;
    }

    public void SetODERDESCRIPTION(String ODERDESCRIPTION) {
        this.ODERDESCRIPTION = ODERDESCRIPTION;
    }

    public String GetODERDESCRIPTION() {
        return ODERDESCRIPTION;
    }

    public void SetCREATEUSER(String CREATEUSER) {
        this.CREATEUSER = CREATEUSER;
    }

    public String GetCREATEUSER() {
        return CREATEUSER;
    }

    public void SetAUDITPERSON(String AUDITPERSON) {
        this.AUDITPERSON = AUDITPERSON;
    }

    public String GetAUDITPERSON() {
        return AUDITPERSON;
    }

    public void SetEXECUTEPERSON(String EXECUTEPERSON) {
        this.EXECUTEPERSON = EXECUTEPERSON;
    }

    public String GetEXECUTEPERSON() {
        return EXECUTEPERSON;
    }

    public void SetORDERTYPE(String ORDERTYPE) {
        this.ORDERTYPE = ORDERTYPE;
    }

    public String GetORDERTYPE() {
        return ORDERTYPE;
    }

    public void SetCREATEDATE(String CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String GetCREATEDATE() {
        return CREATEDATE;
    }

    public void SetAUDITTIME(String AUDITTIME) {
        this.AUDITTIME = AUDITTIME;
    }

    public String GetAUDITTIME() {
        return AUDITTIME;
    }

    public void SetENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public String GetENDTIME() {
        return ENDTIME;
    }

    public void SetORDERSTATUS(String ORDERSTATUS) {
        this.ORDERSTATUS = ORDERSTATUS;
    }

    public String GetORDERSTATUS() {
        return ORDERSTATUS;
    }

    public void SetORDERRECODER(String ORDERRECODER) {
        this.ORDERRECODER = ORDERRECODER;
    }

    public String GetORDERRECODER() {
        return ORDERRECODER;
    }

    public void SetALLPERSON(String ALLPERSON) {
        this.ALLPERSON = ALLPERSON;
    }

    public String GetALLPERSON() {
        return ALLPERSON;
    }


}
