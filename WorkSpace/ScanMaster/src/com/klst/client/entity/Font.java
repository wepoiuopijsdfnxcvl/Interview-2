package com.klst.client.entity;

public enum Font {
	/*DEFAULT("tt7268m_.ttf"),*/ 
	MSBOLD("wqy-microhei.ttc"), FOUNDER("fangzhengweibei.ttf")
	;
	
	private String value;
	
	private Font(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public static Font getFont(String value){
		for(Font f : Font.values()){
			if(f.getValue().equals(value))
				return f;
		}
		return Font.MSBOLD;
	}
}
