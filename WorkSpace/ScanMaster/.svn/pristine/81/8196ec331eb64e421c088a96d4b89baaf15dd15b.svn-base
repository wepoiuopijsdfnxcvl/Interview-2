package com.klst.client.entity;

/**
 * Created on 2015/6/9.
 */
public enum Align {
	
	TOP(95, 50, "TOP"), Middle(50, 50, "BOTTOM"), BOTTOM(95, 50, "BOTTOM"), TopLeft(5, 95, "TOPLEFT"), TopRight(5, 95, "TOPRIGHT"), BottomLeft(95, 95, "BOTTOMLEFT"), BottomRight(95, 95, "BOTTOMRIGHT");
	
	private int vertical;
	private int horizontal;
	private String value;
	
	private Align(int vertical, int horizontal, String value){
		this.value = value;
		this.horizontal = horizontal;
		this.vertical = vertical;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public int getVertical(){
		return this.vertical;
	}
	
	public int getHorizontal(){
		return this.horizontal;
	}
	
	public static Align getAlign(String value){
		for(Align al : Align.values())
		{
			if(al.getValue().equals(value) && al != Middle)
				return al;
		}
		return Align.BOTTOM;
	}
    
}
