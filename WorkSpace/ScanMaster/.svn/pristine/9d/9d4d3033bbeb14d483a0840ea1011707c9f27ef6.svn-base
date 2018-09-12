package com.klst.client.entity;

import com.klst.client.Interface.IEnum;

/**
 * Created on 2015/6/9.
 */
public enum VideoProtocol implements IEnum {

    AUTO("AUTO"),
    H261("H.261"),
    H263("H.263"),
    H264("H.264"),
    H264_HP("H.264-HP");

    private String value;

    private VideoProtocol(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    public static VideoProtocol getVideoProtocol(String value)
    {
        for(VideoProtocol videoProtocol : VideoProtocol.values())
        {
            if(videoProtocol.getValue().equals(value))
                return videoProtocol;
        }

        return null;
    }
}
