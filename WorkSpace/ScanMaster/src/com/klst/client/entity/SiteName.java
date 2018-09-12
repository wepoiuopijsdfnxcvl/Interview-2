package com.klst.client.entity;

/**
 * Created on 2015/6/9.
 */
public class SiteName {

    private SiteNameStatus displayMode;
    private int fontSize;
    private Color color = Color.WhiteBlue;
    private Align textPosition;
    private int transparent;
    private Font font;

    public SiteNameStatus getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(SiteNameStatus displayMode) {
        this.displayMode = displayMode;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Align getTextPosition() {
        return textPosition;
    }

    public void setTextPosition(Align textPosition) {
        this.textPosition = textPosition;
    }

    public int getTransparent() {
        return transparent;
    }

    public void setTransparent(int transparent) {
        this.transparent = transparent;
    }

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
    
}
