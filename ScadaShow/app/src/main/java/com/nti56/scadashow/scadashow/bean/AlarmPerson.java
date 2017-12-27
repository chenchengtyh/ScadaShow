package com.nti56.scadashow.scadashow.bean;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by chencheng on 2017/8/24.
 */

public class AlarmPerson implements Serializable {

    private String STATUS;
    private String PERSON;

    public void SetSTATUS(String STATUS){
        this.STATUS = STATUS;
    }

    public String GetSTATUS(){
        return STATUS;
    }

    public void SetPerson(String PERSON){
        this.PERSON = PERSON;
    }

    public String GetPerson(){
        return PERSON;
    }

}
