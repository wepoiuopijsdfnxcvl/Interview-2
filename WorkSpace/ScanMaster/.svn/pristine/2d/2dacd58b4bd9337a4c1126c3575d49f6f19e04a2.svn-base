package com.klst.client.entity;

import com.klst.client.Interface.IEnum;

/**
 * Created on 2015/6/5.
 */
public enum ProtocolType implements IEnum {
    H323("H.323"),
    SIP("SIP");

    private String value;

    private ProtocolType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    public static ProtocolType getProtocol(String value)
    {
        for(ProtocolType protocolType : ProtocolType.values())
        {
            if(protocolType.getValue().equals(value))
                return protocolType;
        }

        return null;
    }
}
