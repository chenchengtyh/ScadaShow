package com.nti56.scadashow.scadashow.bean;

import java.io.Serializable;

/**
 * Created by chencheng on 2017/8/25.
 */

public class AlarmsStatic implements Serializable {

    //private String NOW_RUNTIME="";
    private String NOW_FAULTTIME="";
    private String NOW_FAULTNUMBER="";
    private String HIS_RUNTIME="";
    private String HIS_FAULTTIME="";
    private String HIS_FAULTNUMBER="";
    //private String HIS_PRODUCTION="";

//    public void SetNOW_RUNTIME(String NOW_RUNTIME) {
//        this.NOW_RUNTIME = NOW_RUNTIME;
//    }
//
//    public String GetNOW_RUNTIME() {
//        return NOW_RUNTIME;
//    }

    public void SetNOW_FAULTTIME(String NOW_FAULTTIME) {
        this.NOW_FAULTTIME = NOW_FAULTTIME;
    }

    public String GetNOW_FAULTTIME() {
        return NOW_FAULTTIME;
    }

    public void SetNOW_FAULTNUMBER(String NOW_FAULTNUMBER) {
        this.NOW_FAULTNUMBER = NOW_FAULTNUMBER;
    }

    public String GetNOW_FAULTNUMBER() {
        return NOW_FAULTNUMBER;
    }

    public void SetHIS_RUNTIME(String HIS_RUNTIME) {
        this.HIS_RUNTIME = HIS_RUNTIME;
    }

    public String GetHIS_RUNTIME() {
        return HIS_RUNTIME;
    }

    public void SetHIS_FAULTTIME(String HIS_FAULTTIME) {
        this.HIS_FAULTTIME = HIS_FAULTTIME;
    }

    public String GetHIS_FAULTTIME() {
        return HIS_FAULTTIME;
    }

    public void SetHIS_FAULTNUMBER(String HIS_FAULTNUMBER) {
        this.HIS_FAULTNUMBER = HIS_FAULTNUMBER;
    }

    public String GetHIS_FAULTNUMBER() {
        return HIS_FAULTNUMBER;
    }

//    public void SetHIS_PRODUCTION(String HIS_PRODUCTION) {
//        this.HIS_PRODUCTION = HIS_PRODUCTION;
//    }
//
//    public String GetHIS_PRODUCTION() {
//        return HIS_PRODUCTION;
//    }


}
