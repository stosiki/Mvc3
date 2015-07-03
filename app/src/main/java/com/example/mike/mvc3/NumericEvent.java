package com.example.mike.mvc3;

import java.util.Date;

/**
 * Created by mike on 7/3/2015.
 */
public class NumericEvent extends SimpleEvent {
    private int numValue;

    public NumericEvent(Date timeStamp, int numValue) {
        super(timeStamp);
        this.numValue = numValue;
    }

    public int getNumValue() {
        return numValue;
    }


}
