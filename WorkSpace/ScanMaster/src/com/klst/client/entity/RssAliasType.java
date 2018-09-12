package com.klst.client.entity;

import com.klst.client.Interface.IEnum;

/**
 * Created on 2015/6/11.
 */
public enum RssAliasType implements IEnum {
    E164("E.164");

    private String value;

    private RssAliasType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    public static RssAliasType getRssAliasType(String value)
    {
        for(RssAliasType rssAliasType : RssAliasType.values())
        {
            if(rssAliasType.getValue().equals(value))
                return rssAliasType;
        }

        return null;
    }
}
