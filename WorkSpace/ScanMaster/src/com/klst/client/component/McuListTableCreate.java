package com.klst.client.component;

import com.klst.client.customplugin.McuListTableModel;
import com.klst.client.entity.McuInfo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class McuListTableCreate {

    private static String[] columnNames = {"","序号", "MCU名称", "MCU地址", "矩阵输入端口","矩阵输出端口"};

    private static Class[] typeArray = {Boolean.class, String.class, String.class, String.class, String.class, String.class};

    private McuListTableCreate(){}

    public static McuListTableCreate getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    //单例模式
    private  static enum Singleton{
        INSTANCE;
        private McuListTableCreate mcuListTableCreate;

        Singleton(){
            mcuListTableCreate = new McuListTableCreate();
        }

        public McuListTableCreate getInstance(){
            return mcuListTableCreate;
        }
    }

    public static JTable createJTable(List<McuInfo> mcuInfoList){
        McuListTableModel mcuListTableModel = new McuListTableModel();
        mcuListTableModel.setColumnNames(columnNames);
        mcuListTableModel.setTypeArray(typeArray);
        Object[][] rowData = getRowData(mcuInfoList);
        mcuListTableModel.setRowData(rowData);
        JTable jTable = new JTable(mcuListTableModel);
        return jTable;
    }

    public static Object[][] getRowData(List<McuInfo> mcuInfoList){
        Object[][] rowData = new Object [mcuInfoList.size()][columnNames.length];
        for(int i=0,len=mcuInfoList.size(); i<len; i++){
            rowData[i][0] = new Boolean(false);
            rowData[i][1] = i + 1;
            rowData[i][2] = mcuInfoList.get(i).getMcuName();
            rowData[i][3] = mcuInfoList.get(i).getMcuIp();
            rowData[i][4] = mcuInfoList.get(i).getPort();
            rowData[i][5] = mcuInfoList.get(i).getOutPort();
        }
        return rowData;
    }

    public static List<McuInfo> getMcuInfoList(Object[][] rowData){
        List<McuInfo> mcuInfos = new ArrayList<>();
        for(int i=0,len=rowData.length; i<len; i++){
            McuInfo mcuInfo = new McuInfo();
            mcuInfo.setMcuName((String) rowData[i][2]);
            mcuInfo.setMcuIp((String) rowData[i][3]);
            mcuInfo.setPort((String) rowData[i][4]);
            mcuInfo.setOutPort((String) rowData[i][5]);
            mcuInfos.add(mcuInfo);
        }
        return mcuInfos;
    }

    public static Object[][] rowDataAdd(Object[][] rowData, McuInfo mcuInfo){
        Object[][] newRowData = new Object [rowData.length + 1][columnNames.length];
        for(int i=0,len=rowData.length; i<len;i++){
            newRowData[i] = rowData[i];
        }
        newRowData[newRowData.length-1] = new Object[]{new Boolean(false), newRowData.length, mcuInfo.getMcuName(), mcuInfo.getMcuIp(), mcuInfo.getPort(),mcuInfo.getOutPort()};
        return newRowData;
    }

}
