package com.example.mike.mvc3;

import java.util.Date;

/**
 * Created by mike on 7/3/2015.
 */
@Immutable
public class SimpleEvent {
    private Date timeStamp;

    public SimpleEvent(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
