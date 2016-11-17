package com.chub.ws.producer.model;

/**
 * Created by vsafronovici on 10/20/2016.
 */
public class Level3 {

    public Level3() {

    }

    public Level3(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    private String field1;
    private String field2;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
