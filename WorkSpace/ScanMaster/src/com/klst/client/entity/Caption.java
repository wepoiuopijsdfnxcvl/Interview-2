package com.klst.client.entity;

/**
 * Created on 2015/6/9.
 */
public class Caption {

    private boolean enabled;
    private String text;
    private int fontSize;
    private Color color;
    private int playTimes;
    private int playSpeed;
    private Align textPosition;
    private int transparent;
    private Font font;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    public int getPlaySpeed() {
        return playSpeed;
    }

    public void setPlaySpeed(int playSpeed) {
        this.playSpeed = playSpeed;
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
