package com.klst.client.entity;

import com.klst.client.Interface.IEnum;

/**
 * Created on 2015/6/12.
 */
public enum Color implements IEnum {

    RedWhite("0x00ff0000","0x00ffffff"),
    WhiteBlack("0x00ffffff","0x00000000"),
    WhiteGray("0x00ffffff","0x00808080"),
    WhiteMaroon("0x00ffffff","0x00800000"),
    WhiteBrown("0x00ffffff","0x009a7057"),
    WhiteNavy("0x00ffffff","0x00000080"),
    WhiteOlive("0x00ffffff","0x00808000"),
    WhiteGreen("0x00ffffff","0x00008000"),
    WhitePurple("0x00ffffff","0x00800080"),
    YellowBlack("0x00ffff00","0x00000000"),
    YellowNavy("0x00ffff00","0x00000080"),
    AquaBlack("0x0000ffff","0x00000000"),
    BlueWhite("0x000000ff","0x00ffffff"),
    LimeBlack("0x0000ff00","0x00000000"),
    BlackSliver("0x00000000","0x00c0c0c0"),
    BlackWhite("0x00000000","0x00ffffff"),
    WhiteBlue("0x00ffffff", "0x001b2086"), //same as UE style
    ;

    private String color;
    private String backColor;


    private Color(String color, String backColor)
    {
        this.color = color;
        this.backColor = backColor;
    }

    public String getValue(){
        return null;
    }
    
    public String getColor()
    {
    	return this.color;
    }
    
    public String getBackground()
    {
    	return this.backColor;
    }

   public static Color getColorObj(String color, String bgColor)
   {
	   for(Color tmp : Color.values())
	   {
		   if(tmp.getColor().equals(color) && tmp.getBackground().equals(bgColor))
			   return tmp;
	   }
	   return Color.WhiteBlue;
		   
   }

}
