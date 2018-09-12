package com.klst.client.entity;

import com.klst.client.Interface.IEnum;

/**
 * Created on 2015/6/9.
 */
public enum VideoMode implements IEnum {
    LectureMode("LECTURE"),SameLayout("SAME");
    private String value;

    private VideoMode(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    public static VideoMode getVideoMode(String value)
    {
        for(VideoMode videoMode : VideoMode.values())
        {
            if(videoMode.getValue().equals(value))
                return videoMode;
        }

        return null;
    }
}
