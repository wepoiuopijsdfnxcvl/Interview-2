package com.klst.client.util;

import java.io.*;

public class FileUtils {

    public static String readFile(String path){
        StringBuffer sbf = new StringBuffer();
        BufferedReader br = null;
        String line = null;
        try {
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
                return "";
            }
            br = new BufferedReader(new FileReader(path));
            while((line = br.readLine()) != null){
                sbf.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    public static void writeFile(String str, String path, boolean flag){
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file, flag));
            bw.write(str);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
