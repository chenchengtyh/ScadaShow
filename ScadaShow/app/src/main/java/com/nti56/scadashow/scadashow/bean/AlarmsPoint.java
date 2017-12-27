package com.nti56.scadashow.scadashow.bean;

import java.io.Serializable;

/**
 * Created by chencheng on 2017/8/25.
 */

public class AlarmsPoint implements Serializable {

    private String TOTALFAULTTIME;
    private String T_DATE;

    public void SetTOTALFAULTTIME(String TOTALFAULTTIME) {
        this.TOTALFAULTTIME = TOTALFAULTTIME;
    }

    public String GetTOTALFAULTTIME() {
        return TOTALFAULTTIME;
    }

    public void SetT_DATE(String T_DATE) {
        this.T_DATE = T_DATE;
    }

    public String GetT_DATE() {
        return T_DATE;
    }
}
