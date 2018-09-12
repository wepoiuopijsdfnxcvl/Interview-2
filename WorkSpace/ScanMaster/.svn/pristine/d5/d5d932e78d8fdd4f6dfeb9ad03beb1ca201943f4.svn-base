package com.klst.client.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.klst.client.Thread.EventMessageReceiveTask;
import com.klst.client.component.TerminalScanUpdate;
import com.klst.client.entity.McuInfo;
import com.klst.client.entity.PollingPartInfo;
import com.klst.client.util.Constants;
import com.klst.client.util.FileUtils;
import com.klst.client.util.HttpClientHelper;
import com.klst.client.util.Log;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class McuScanService {

    private static final Logger LOGGER = Log.getLogger(McuScanService.class.getSimpleName());

    public static String mcuScanFlag = Constants.MCU_SCAN_STOP;

    private ConcurrentHashMap<String, BlockingQueue> eventQueueMap = new ConcurrentHashMap<>();

    private List<McuInfo> mcuList = new ArrayList<>();

    private String matrixInfo;

    private McuInfo currentMcuInfo = new McuInfo();

    private TerminalScanUpdate terminalScanUpdate;

    private Thread mcuScanThread;

    private Thread eventThread;

    private McuScanService(){ }

    public static McuScanService getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    //单例模式
    private  static enum Singleton{
        INSTANCE;
        private McuScanService mcuScanService;

        Singleton(){
            mcuScanService = new McuScanService();
        }

        public McuScanService getInstance(){
            return mcuScanService;
        }
    }

    public void loadMcus(){
        String mcuStr = FileUtils.readFile(Constants.MCU_JSON_FILE);
        mcuList = JSONArray.parseArray(mcuStr, McuInfo.class);
        if(mcuList == null){
            mcuList = new ArrayList<>();
        }
    }

    public void saveMcus(){
        String mcuStr = JSON.toJSONString(mcuList);
        FileUtils.writeFile(mcuStr, Constants.MCU_JSON_FILE, false);
    }

    public void loadMatrix(){
        matrixInfo = FileUtils.readFile(Constants.MATRIX_JSON_FILE);
        if(matrixInfo == null){
            matrixInfo = "";
        }
    }

    public void saveMatrix(){
        FileUtils.writeFile(matrixInfo, Constants.MATRIX_JSON_FILE, false);
    }

    //MCU开始轮巡主方法
    public void startScan(JFrame rootjFrame, JPanel scanPanel, JButton stopButton, JList mcuJlist){
        mcuScanFlag = Constants.MCU_SCAN_START;
        mcuJlist.setSelectedIndices(new int[]{});
        mcuScanThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(mcuScanFlag == Constants.MCU_SCAN_START
                        || mcuScanFlag == Constants.MCU_SCAN_SUSPEND){//运行或暂停状态
                    synchronized(eventQueueMap) {
                        for (int i = 0, len = mcuList.size(); i < len; i++) {
                            try {
                                if(mcuScanFlag == Constants.MCU_SCAN_STOP){//停止轮巡
                                    return;
                                }
                                currentMcuInfo = mcuList.get(i);
                                 //高亮选择MCU列表
                                mcuJlist.setSelectedIndex(i);
                                LOGGER.info("Load MCU, Name = " + currentMcuInfo.getMcuName() + ", IP = " + currentMcuInfo.getMcuIp());
                                TerminalScanUpdate.isStop = true;
                                EventMessageReceiveTask.isStop = true;
                                terminalScanUpdate = new TerminalScanUpdate(rootjFrame, scanPanel, stopButton, currentMcuInfo);
                                if(TerminalScanUpdate.noScan){
                                    continue;
                                }
                                //开始MCU轮巡
                                httpScanCtrl(true, null);
                                //唤醒eventThread
                                LOGGER.info("mcuScanThread notify eventThread!");
                                eventQueueMap.notify();

                                //当前MCU终端轮巡中，当前线程阻塞，等待唤醒
                                LOGGER.info("mcuScanThread wait!");
                                eventQueueMap.wait();
                                eventQueueMap.clear();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        mcuScanThread.start();

        eventThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mcuScanFlag == Constants.MCU_SCAN_START
                        || mcuScanFlag == Constants.MCU_SCAN_SUSPEND){//运行或暂停状态
                    synchronized(eventQueueMap){
                        try {
                            if(mcuScanFlag == Constants.MCU_SCAN_SUSPEND){//暂停
                                LOGGER.info("eventThread suspend!");
                                synchronized(Constants.MCU_SCAN_SUSPEND){
                                    Constants.MCU_SCAN_SUSPEND.wait();
                                }
                                LOGGER.info("eventThread continue!");
                            }
                            if(mcuScanFlag == Constants.MCU_SCAN_STOP){//停止轮巡
                                return;
                            }
                            BlockingQueue<String> eventQueue = eventQueueMap.get(currentMcuInfo.getMcuIp());
                            if(eventQueue != null){
                                String scanFinishDetail = eventQueue.poll(2, TimeUnit.SECONDS);
                                if(scanFinishDetail != null){
                                    PollingPartInfo pollingPartInfo = (PollingPartInfo) JSON.parseObject(scanFinishDetail, PollingPartInfo.class);
                                    if(pollingPartInfo.getMeetingId().equals(terminalScanUpdate.getMeetingInfo().getId())
                                            && "1".equals(pollingPartInfo.getScanFinish())){//轮巡一轮结束
                                        LOGGER.info(currentMcuInfo.getMcuIp() +" scan finish!");
                                        TerminalScanUpdate.isStop = true;
                                        EventMessageReceiveTask.isStop = true;
                                        //停止轮巡
                                        httpScanCtrl(false, "0");

                                        LOGGER.info("eventThread notify mcuScanThread!");
                                        eventQueueMap.notify();
                                        LOGGER.info("eventThread wait!");
                                        eventQueueMap.wait();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        eventThread.start();

    }

    public void stopScan(){
        mcuScanFlag = Constants.MCU_SCAN_STOP;
        eventQueueMap.clear();
        synchronized(Constants.MCU_SCAN_SUSPEND){
            Constants.MCU_SCAN_SUSPEND.notifyAll();
        }
        synchronized(eventQueueMap){
            eventQueueMap.notifyAll();
        }
        //停止MCU轮巡
        try {
            httpScanCtrl(false, "2");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //清除pollingId颜色
        if(terminalScanUpdate != null && terminalScanUpdate.getMeetingInfo() != null){
            pollingPartPanelUpdate();
        }
    }

    public void suspendScan(){
        mcuScanFlag = Constants.MCU_SCAN_SUSPEND;
        //暂停MCU轮巡
        try {
            httpScanCtrl(false, "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void continueScan(){
        mcuScanFlag = Constants.MCU_SCAN_START;
        synchronized(Constants.MCU_SCAN_SUSPEND){
            Constants.MCU_SCAN_SUSPEND.notify();
        }
        //开始MCU轮巡
        try {
            httpScanCtrl(true, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<McuInfo> getMcuList() {
        return mcuList;
    }

    public void setMcuList(List<McuInfo> mcuList) {
        this.mcuList = mcuList;
    }

    public String getMatrixInfo() {
        return matrixInfo;
    }

    public void setMatrixInfo(String matrixInfo) {
        this.matrixInfo = matrixInfo;
    }

    public ConcurrentHashMap<String, BlockingQueue> getEventQueueMap() {
        return eventQueueMap;
    }

    public void setEventQueueMap(ConcurrentHashMap<String, BlockingQueue> eventQueueMap) {
        this.eventQueueMap = eventQueueMap;
    }

    public TerminalScanUpdate getTerminalScanUpdate() {
        return terminalScanUpdate;
    }

    public void setTerminalScanUpdate(TerminalScanUpdate terminalScanUpdate) {
        this.terminalScanUpdate = terminalScanUpdate;
    }

    private synchronized void httpScanCtrl(Boolean startFlag, String flag) throws Exception {
        if(terminalScanUpdate != null && terminalScanUpdate.getMeetingInfo() != null){
            String scanUrl = "http://" + currentMcuInfo.getMcuIp() +"/portal/rest/meetings/"+terminalScanUpdate.getMeetingInfo().getId()+"/autoScan";
            if(startFlag){
                HttpClientHelper.doPost(scanUrl, null);
            }else{
                scanUrl += "?flag="+flag;
                HttpClientHelper.doDelete(scanUrl);
            }
        }
    }

    private void pollingPartPanelUpdate(){
        JPanel partListPanel = terminalScanUpdate.getPartListPanel();
        Component[] components = partListPanel.getComponents();
        for(int i=0,len=components.length; i<len; i++){
            JPanel partPanel = (JPanel) components[i];
            JLabel jLabel = (JLabel) partPanel.getComponent(0);
            Color background = partPanel.getBackground();
            if(background.getRGB() == new Color(248,251,108).getRGB()){
                partPanel.setBackground(new Color(0,193,97));
                jLabel.setForeground(Color.BLACK);
            }
        }
        partListPanel.updateUI();
    }
}
