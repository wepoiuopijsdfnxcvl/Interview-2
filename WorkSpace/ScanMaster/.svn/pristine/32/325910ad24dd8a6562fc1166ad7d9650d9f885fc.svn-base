package com.klst.client.entity;

public enum VideoFormat {
	AUTO("AUTO"),
	V_1080P("1080P"),
	V_720P("720P"),
	V_4CIF_4("4CIF/4SIF_4:3"),
	V_4CIF_16("4CIF/4SIF_16:9"),
	V_CIF("CIF/SIF"),
	;
	
	private String value;
	
	private VideoFormat(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public static VideoFormat getVideoFormat(String value){
		for(VideoFormat vf : VideoFormat.values())
		{
			if(vf.getValue().equals(value))
				return vf;
		}
		return null;
	}
}
