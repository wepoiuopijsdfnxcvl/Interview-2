package web.laf.lite.widget;

import java.util.Arrays;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Register{
	static Preferences prefs;
	static List<String> keyList;
	static Integer counter = new Integer(0);
	
	static Register lafsRegister;
	static Register themesRegister;
	
	int index = 0;
	
	public static void init(String appname){
		prefs = Preferences.userRoot().node(appname);
		try {
			Register.keyList = Arrays.asList(prefs.keys());
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		lafsRegister = new Register(103);
		themesRegister = new Register(104);
		Register.registerString(lafsRegister, "web.laf.lite.ui.WebLookAndFeelLite");
		Register.registerString(themesRegister, "default");
	}
	
	public Register(){
		
	}
	
	public Register(int value){
		index = value;
	}
	
	public void setValue(int value){
		index = value;
	}
	
	public int getValue(){
		return index;
	}
	
	public static void registerBoolean(Register reg){
		if(reg.getValue() == 0){
			counter++;
			reg.setValue(counter.intValue());
		}
		if(!keyList.contains(""+reg.getValue()))
			prefs.putBoolean(""+reg.getValue(), false);
	}
	
	public static void putBoolean(Register reg, boolean value){
		prefs.putBoolean(""+reg.getValue(), value);
		flush();
	}
	
	public static boolean getBoolean(Register reg){
		return prefs.getBoolean(""+reg.getValue(), false);
	}
	
	public static void registerString(Register reg){
		if(reg.getValue() == 0){
			counter++;
			reg.setValue(counter.intValue());
		}
		if(!keyList.contains(""+reg.getValue()))
			prefs.put(""+reg.getValue(), "");
	}
	
	public static void registerString(Register reg, String defValue){
		if(reg.getValue() == 0){
			counter++;
			reg.setValue(counter.intValue());
		}
		if(!keyList.contains(""+reg.getValue()))
			prefs.put(""+reg.getValue(), defValue);
	}
	
	public static void putString(Register reg, String value){
		prefs.put(""+reg.getValue(), value);
		flush();
	}
	
	public static String getString(Register reg){
		return prefs.get(""+reg.getValue(), "");
	}
	
	public static void flush(){
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	public static void setTheme(String iconName) {
		Register.putString(themesRegister, iconName);
	}
	
	public static String getTheme() {
		return Register.getString(themesRegister);
	}
	
	public static void setLaf(String lafName) {
		Register.putString(lafsRegister, lafName);
	}
	
	public static String getLaf() {
		return Register.getString(lafsRegister);
	}
}
