package com.example.mike.mvc3;

import java.util.Date;

/**
 * Created by mike on 7/3/2015.
 */
@Immutable
public class EventLineDescriptor {
    private String title;
    private int type;

    public EventLineDescriptor(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
