package com.nti56.scadashow.scadashow.bean;

import java.io.Serializable;

/**
 * Created by chencheng on 2017/8/31.
 */

public class APaddr implements Serializable {

    private String IPADDRESS;
    private String ADDRESS;

    public void setIPADDRESS(String ADDRESS) {
        this.IPADDRESS = ADDRESS;
    }

    public String getIPADDRESS() {
        return IPADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getADDRESS() {
        return ADDRESS;
    }
}

