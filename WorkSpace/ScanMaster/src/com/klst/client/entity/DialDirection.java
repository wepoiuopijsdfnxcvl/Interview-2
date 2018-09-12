package com.klst.client.entity;

import com.klst.client.Interface.IEnum;

/**
 * Created on 2015/6/5.
 */
public enum DialDirection implements IEnum {
    IN("Dial-in"),
    OUT("Dial-out");

    private String value;

    private DialDirection(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    public static DialDirection getDialDirection(String value)
    {
        for(DialDirection dialDirection : DialDirection.values())
        {
            if(dialDirection.getValue().equals(value))
                return dialDirection;
        }

        return null;
    }
}
