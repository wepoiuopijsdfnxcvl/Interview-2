package com.klst.client.entity;

/**
 * Created on 2015/6/9.
 */
public class Layout {

    private LayoutMode mode;
    private LayoutId layoutId;
    private boolean isLectureSiteName;

    public LayoutMode getMode() {
        return mode;
    }

    public void setMode(LayoutMode mode) {
        this.mode = mode;
    }

    public LayoutId getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(LayoutId layoutId) {
        this.layoutId = layoutId;
    }

    public boolean isLectureSiteName() {
        return isLectureSiteName;
    }

    public void setLectureSiteName(boolean lectureSiteName) {
        isLectureSiteName = lectureSiteName;
    }
}
