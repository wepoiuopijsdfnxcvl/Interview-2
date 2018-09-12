package com.klst.client.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.klst.client.Thread.EventMessageReceiveTask;
import com.klst.client.customplugin.CustomCellRender;
import com.klst.client.customplugin.ParticipantListModel;
import com.klst.client.entity.*;
import com.klst.client.service.McuScanService;
import com.klst.client.util.Constants;
import com.klst.client.util.HttpClientHelper;
import com.klst.client.util.Log;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class TerminalScanUpdate {

    private static final Logger LOGGER = Log.getLogger(TerminalScanUpdate.class.getSimpleName());

    private JFrame rootjFrame;

    private JPanel scanPanel;

    private JPanel scanSetPanel;

    private JPanel partListPanel;

    private JButton setScanListButton;

    private JButton stopButton;

    private McuInfo mcuInfo;

    private String mcuIp;

    private MeetingInfo meetingInfo;

    private List<ParticipantInfo> participantList;

    private List<ParticipantInfo> leftDataList;

    private List<ParticipantInfo> rightDataList;

    private BlockingQueue<String> eventMessageQueue = new LinkedBlockingDeque();

    private int online = 0;

    private JScrollPane meetingPanel;

    public static  Boolean isStop = false;

    public static  Boolean noScan = false;

    public TerminalScanUpdate(JFrame rootjFrame, JPanel scanPanel, JButton stopButton, McuInfo mcuInfo){
        this.rootjFrame = rootjFrame;
        this.scanPanel = scanPanel;
        this.stopButton = stopButton;
        this.mcuInfo = mcuInfo;
        this.mcuIp = mcuInfo.getMcuIp();
        this.isStop = false;
        this.noScan = false;

        init();

        new EventMessageReceiveTask(mcuIp, 8888, eventMessageQueue).start();

    }

    public McuInfo getMcuInfo() {
        return mcuInfo;
    }

    public void setMcuInfo(McuInfo mcuInfo) {
        this.mcuInfo = mcuInfo;
    }

    public MeetingInfo getMeetingInfo() {
        return meetingInfo;
    }

    public void setMeetingInfo(MeetingInfo meetingInfo) {
        this.meetingInfo = meetingInfo;
    }

    public JButton getSetScanListButton() {
        return setScanListButton;
    }

    public void setSetScanListButton(JButton setScanListButton) {
        this.setScanListButton = setScanListButton;
    }

    public JPanel getPartListPanel() {
        return partListPanel;
    }

    public void setPartListPanel(JPanel partListPanel) {
        this.partListPanel = partListPanel;
    }

    public void init(){
//        scanPanel.setBorder(BorderFactory.createTitledBorder(mcuInfo.getMcuName() + " "+ Constants.SCAN_LIST_NAME));
        scanPanel.removeAll();
        try {
            String meetingUrl = "http://" + mcuIp +"/portal/rest/meetings?pageIndex=1&pageSize=100";
            String meetingContent = HttpClientHelper.doGetWithRole(meetingUrl);
            JSONObject jsonObject =  JSON.parseObject(meetingContent);
            List<MeetingInfo> meetingInfoList = JSONArray.parseArray(jsonObject.getJSONArray("details").toJSONString(), MeetingInfo.class);
            if(meetingInfoList == null || meetingInfoList.size() == 0){
                scanPanel.updateUI();
                showDialog("当前无会议");
                noScan = true;
                return;
            }
            meetingInfo = meetingInfoList.get(0);
            if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_START
                &&(meetingInfo.getScanList() == null || meetingInfo.getScanList().size() == 0)){
                scanPanel.updateUI();
                showDialog("轮巡列表为空");
                noScan = true;
                return;
            }
            String participantsUrl = "http://" + mcuIp +"/portal/rest/meetings/"+meetingInfo.getId()+"/participants?pageIndex=0&pageSize=100";
            String participantsContent = HttpClientHelper.doGetWithRole(participantsUrl);
            JSONObject ptsJsonObject =  JSON.parseObject(participantsContent);
            participantList = JSONArray.parseArray(ptsJsonObject.getJSONArray("details").toJSONString(), ParticipantInfo.class);

            //初始化mcu基本信息面板
            createMcuInfoPanel();

            //初始化终端会议面板
            createParticipantPanel();

            scanPanel.updateUI();

            updateMeetingScanStatus();
        } catch (Exception e) {
            scanPanel.updateUI();
            showDialog("为无效MCU地址");
            noScan = true;
            return;
        }
    }

    private void showDialog(String info){
        String titleMessage =  mcuInfo.getMcuName() + " " + info + ",本对话框 5 秒后关闭";
        int optionType = JOptionPane.DEFAULT_OPTION;
        if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_START){
            titleMessage += ",是否结束轮巡";
            optionType = JOptionPane.OK_CANCEL_OPTION;
        }
        titleMessage += "！";
        JOptionPane optionPane = new JOptionPane(titleMessage, JOptionPane.WARNING_MESSAGE, optionType);
        final JDialog dialog = optionPane.createDialog(null,Constants.WARNING);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=5; i>0; i--){
                    if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_START){
                        optionPane.setMessage(mcuInfo.getMcuName() + " " + info + ",本对话框 " + i +" 秒后关闭,是否结束轮巡！");
                    }else{
                        optionPane.setMessage(mcuInfo.getMcuName() + " " + info + ",本对话框 " + i +" 秒后关闭！");
                    }
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dialog.setVisible(false);
                dialog.dispose();
            }
        }).start();
        dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(true);
        dialog.setVisible(true);
        Object selectValue = optionPane.getValue();
        if(selectValue instanceof Integer){
            int option = (Integer) optionPane.getValue();
            if(option == 0){
                if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_START){
                    stopButton.doClick();
                }
            }
        }
    }

    private void initScanDatas(){
        List<String> scanList = meetingInfo.getScanList();
        leftDataList = new ArrayList<>();
        rightDataList = new ArrayList<>();
        for(int i=0,len=participantList.size(); i<len; i++){
            if(!scanList.contains(participantList.get(i).getId())
                    && participantList.get(i).getStatus() == ConnStatus.CONNECTED){
                leftDataList.add(participantList.get(i));
            }
        }
        for(int j=0,lej=scanList.size(); j<lej; j++){
            for(int i=0,len=participantList.size(); i<len; i++){
                if(scanList.get(j).equals(participantList.get(i).getId())
                        && participantList.get(i).getStatus() == ConnStatus.CONNECTED){
                    rightDataList.add(participantList.get(i));
                }
            }
        }
    }

    private void createMcuInfoPanel(){
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel gridLayoutPanel = new JPanel(new GridLayout(2,4,50,20));
        JPanel tmpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel mcuNameLabel = new JLabel(Constants.MCU_NAME_LABEL + "：");
        tmpPanel.add(mcuNameLabel);
        JLabel mcuName = new JLabel(mcuInfo.getMcuName());
        tmpPanel.add(mcuName);
        gridLayoutPanel.add(tmpPanel);

        tmpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel mcuIpLabel = new JLabel(Constants.MCU_ADDRESS_LABEL + "：");
        tmpPanel.add(mcuIpLabel);
        JLabel mcuIpValue = new JLabel(mcuInfo.getMcuIp());
        tmpPanel.add(mcuIpValue);
        gridLayoutPanel.add(tmpPanel);

        tmpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel mcuPortLabel = new JLabel(Constants.MCU_PORT_LABEL + "：");
        tmpPanel.add(mcuPortLabel);
        JLabel mcuPort = new JLabel(mcuInfo.getPort());
        tmpPanel.add(mcuPort);
        gridLayoutPanel.add(tmpPanel);

        tmpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel mcuOutPortLabel = new JLabel(Constants.MCU_PORT_OUT_LABEL + "：");
        tmpPanel.add(mcuOutPortLabel);
        JLabel mcuOutPort = new JLabel(mcuInfo.getOutPort());
        tmpPanel.add(mcuOutPort);
        gridLayoutPanel.add(tmpPanel);

        tmpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel connectStatusLabel = new JLabel(Constants.MCU_CONNECT_STATUS_LABEL + "：");
        tmpPanel.add(connectStatusLabel);
        JPanel connectStatus = new JPanel();
        connectStatus.setBackground(new Color(0,193,97));
        connectStatus.setPreferredSize(new Dimension(60,20));
        tmpPanel.add(connectStatus);
        gridLayoutPanel.add(tmpPanel);

        tmpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel commonStatusLabel = new JLabel(Constants.MCU_COMMON_STATUS_LABEL + "：");
        tmpPanel.add(commonStatusLabel);
        JPanel commonStatus = new JPanel();
        commonStatus.setBackground(Color.lightGray);
        commonStatus.setPreferredSize(new Dimension(60,20));
        tmpPanel.add(commonStatus);
        gridLayoutPanel.add(tmpPanel);

        tmpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel scanStatusLabel = new JLabel(Constants.MCU_SCAN_STATUS_LABEL + "：");
        tmpPanel.add(scanStatusLabel);
        JPanel scanStatus = new JPanel();
        scanStatus.setBackground(new Color(248,251,108));
        scanStatus.setPreferredSize(new Dimension(60,20));
        tmpPanel.add(scanStatus);
        gridLayoutPanel.add(tmpPanel);

        gridLayoutPanel.add(new JLabel("    "));
        topPanel.add(gridLayoutPanel);
        scanPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void createParticipantPanel(){
        meetingPanel = new JScrollPane();
        meetingPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        meetingPanel.setPreferredSize(new Dimension(900,500));
        partListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        partListPanel.setPreferredSize(new Dimension(900,450));

        //寻找初始轮巡状态的终端id
        List<String> scanList = meetingInfo.getScanList();
        String initScanPartId = "";
        if(scanList != null && scanList.size()>0){
            f1 : for(int j=0,scanLen=scanList.size(); j<scanLen; j++){
                    for(int i=0,len=participantList.size(); i<len; i++){
                        ParticipantInfo participantInfo = participantList.get(i);
                        if(scanList.get(j).equals(participantInfo.getId())
                                && participantInfo.getStatus() == ConnStatus.CONNECTED){
                            initScanPartId = participantInfo.getId();
                            break f1;
                        }
                    }
                }
        }
        for(int i=0,len=participantList.size(); i<len; i++){
            ParticipantInfo participantInfo = participantList.get(i);
            JPanel partPanel = new JPanel(new BorderLayout());
            partPanel.setName(participantInfo.getId());
            partPanel.setPreferredSize(new Dimension(160,100));
            partPanel.setBorder(BorderFactory.createEtchedBorder());
            JLabel jLabel = new JLabel(participantInfo.getName());
            jLabel.setToolTipText(participantInfo.getName());
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            partPanel.add(jLabel, BorderLayout.CENTER);
            if(participantInfo.getStatus() == ConnStatus.CONNECTED){
                online++;
                partPanel.setBackground(new Color(0,193,97));
            }else{
                partPanel.setBackground(Color.lightGray);
            }

            //默认轮巡列表第一个终端高亮
            if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_START
                    && !"".equals(initScanPartId)
                    && initScanPartId.equals(participantInfo.getId())){
                partPanel.setBackground(new Color(248,251,108));
                jLabel.setForeground(new Color(247,111,96));
            }
            partListPanel.add(partPanel);
        }

        meetingPanel.setBorder(BorderFactory.createTitledBorder(Constants.TERMINAL_LIST + "( " + online+" / " + participantList.size() + " )"));
        meetingPanel.setViewportView(partListPanel);
        scanPanel.add(meetingPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setPreferredSize(new Dimension(80,40));
        setScanListButton = new JButton(Constants.SET_SCAN_LIST);
        bottomPanel.add(setScanListButton);
        scanPanel.add(bottomPanel,BorderLayout.SOUTH);
        if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_STOP){
            setScanListButton.setEnabled(true);
        }else{
            setScanListButton.setEnabled(false);
        }
        setScanListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSetScanListDialog(rootjFrame);
            }
        });
    }

    private void updateMeetingScanStatus(){
        //更新轮巡终端状态
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop){
                    try {
                        String messageDetail = eventMessageQueue.poll(2, TimeUnit.SECONDS);
                        if(messageDetail == null){
                            continue;
                        }
                        JSONObject jsonObj = JSONObject.parseObject(messageDetail);
                        String eventType = jsonObj.getString("eventType");
                        String eventAction = jsonObj.getString("eventAction");
                        if(Constants.EVENT_MEETINGCHANGED.equals(eventType)){
                            if(Constants.EVENT_POLLINGPART.equals(eventAction)){
                                PollingPartInfo pollingPartInfo = (PollingPartInfo) JSON.parseObject(jsonObj.getString("detail"), PollingPartInfo.class);
                                Component[] components = partListPanel.getComponents();
                                for(int i=0,len=components.length; i<len; i++){
                                    JPanel partPanel = (JPanel) components[i];
                                    JLabel jLabel = (JLabel) partPanel.getComponent(0);
                                    Color background = partPanel.getBackground();
                                    if(background.getRGB() == new Color(248,251,108).getRGB()){
                                        partPanel.setBackground(new Color(0,193,97));
                                        jLabel.setForeground(Color.BLACK);
                                    }
                                    if(partPanel.getName().equals(pollingPartInfo.getPartId())){
                                        partPanel.setBackground(new Color(248,251,108));
                                        jLabel.setForeground(new Color(247,111,96));
                                    }
                                }
                                partListPanel.updateUI();
                            }else if(Constants.EVENT_MEETING_UPDATED.equals(eventAction)){
                                meetingInfo = (MeetingInfo) JSON.parseObject(jsonObj.getString("detail"), MeetingInfo.class);
                            }else if(Constants.EVENT_MEETING_DELETED.equals(eventAction)){
                                List<String> meetingIds = JSONArray.parseArray(jsonObj.getString("detail"), String.class);
                                if(meetingIds != null && meetingIds.get(0).equals(meetingInfo.getId())){
                                    showDialog("会议结束");
                                    if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_START){
                                        stopButton.doClick();
                                    }
                                    scanPanel.removeAll();
                                    scanPanel.updateUI();
                                    return;
                                }
                            }else if(Constants.EVENT_PARTCOUNTCHANGED.equals(eventAction)){
                                JSONObject detailObj = jsonObj.getJSONObject("detail");
                                String meetingId = detailObj.getString("meetingId");
                                if(meetingInfo.getId().equals(meetingId)){
                                    online = Integer.parseInt(detailObj.getString("online"));
                                    meetingPanel.setBorder(BorderFactory.createTitledBorder(Constants.TERMINAL_LIST + "( " + online+" / " + detailObj.getString("total") + " )"));
                                }
                            }
                        }else if(Constants.EVENT_MEETINGPARTICIPANTCHANGED.equals(eventType)){
                            if(Constants.EVENT_PARTSTATUSCHANGED.equals(eventAction)){
                                JSONObject statusObj = jsonObj.getJSONObject("detail");
                                JSONObject partStatusObj = statusObj.getJSONObject("meetingParticipantId");
                                String partId = partStatusObj.getString("partId").trim();
                                String status = statusObj.getString("status");
                                if(meetingInfo.getId().equals(partStatusObj.getString("meetingId"))){
                                    Component[] components = partListPanel.getComponents();
                                    for(int i=0,len=components.length; i<len; i++){
                                        JPanel partPanel = (JPanel) components[i];
                                        String panelName = partPanel.getName();
                                        if(panelName.equals(partId)){
                                            if(status.equals("CONNECTED") || status.equals("CONNECTING")){
                                                partPanel.setBackground(new Color(0,193,97));
                                            }else if(status.equals("DISCONNECTED")){
                                                partPanel.setBackground(Color.lightGray);
                                            }
                                        }
                                    }
                                    partListPanel.updateUI();

                                    for(int i=0,len=participantList.size(); i<len; i++){
                                        ParticipantInfo participantInfo = participantList.get(i);
                                        if(participantInfo.getId().equals(partId)){
                                            ConnStatus connStatus = ConnStatus.DISCONNECTED;
                                            if(status.equals("CONNECTED")){
                                                connStatus = ConnStatus.CONNECTED;
                                            }else if(status.equals("DISCONNECTED")){
                                                connStatus = ConnStatus.DISCONNECTED;
                                            }
                                            participantInfo.setStatus(connStatus);
                                        }
                                    }
                                }
                            }else if(Constants.EVENT_PART_DELETED.equals(eventAction)){
                                JSONArray jsonArray1 = jsonObj.getJSONArray("detail");
                                String meetingId = jsonArray1.getString(0);
                                JSONArray jsonArray2 = jsonArray1.getJSONArray(1);
                                if(meetingId.equals(meetingInfo.getId())){
                                    Component[] components = partListPanel.getComponents();
                                    int index = -1;
                                    for(int i=0,len=components.length; i<len; i++){
                                        JPanel partPanel = (JPanel) components[i];
                                        if(partPanel.getName().equals(jsonArray2.getString(0))){
                                            index = i;
                                            break;
                                        }
                                    }
                                    if(index != -1){
                                        partListPanel.remove(index);
                                        partListPanel.updateUI();
                                        participantList.remove(index);
                                    }
                                }
                            }else if(Constants.EVENT_PART_CREATED.equals(eventAction)){
                                ParticipantInfo participantInfo = (ParticipantInfo) JSON.parseObject(jsonObj.getString("detail"), ParticipantInfo.class);
                                JPanel partPanel = new JPanel(new BorderLayout());
                                partPanel.setName(participantInfo.getId());
                                partPanel.setPreferredSize(new Dimension(160,100));
                                partPanel.setBorder(BorderFactory.createEtchedBorder());
                                JLabel jLabel = new JLabel(participantInfo.getName());
                                jLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                partPanel.add(jLabel, BorderLayout.CENTER);
                                partPanel.setBackground(Color.lightGray);
                                partListPanel.add(partPanel);
                                partListPanel.updateUI();
                                participantList.add(participantInfo);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void showSetScanListDialog(Frame ownerPanel) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(ownerPanel, Constants.SET_SCAN_LIST, true);
        // 设置对话框的宽高
        double width = Toolkit.getDefaultToolkit().getScreenSize().width - 400;
        double height = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        dialog.setSize((int)width, (int)height);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(ownerPanel);

        JPanel jPanel = new JPanel(new BorderLayout());
        //初始化轮询列表
        initScanDatas();
        //创建设置轮询列表面板
        JButton saveButton = new JButton(Constants.BUTTON_CONFIRM);
        createSetScanPanel(dialog, saveButton);

        jPanel.add(scanSetPanel,BorderLayout.CENTER);

        // 创建一个按钮用于关闭对话框
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(80,40));
        buttonPanel.add(saveButton);
        JButton cancelButton = new JButton(Constants.BUTTON_CANCEL);
        buttonPanel.add(cancelButton);
        jPanel.add(buttonPanel,BorderLayout.SOUTH);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        // 设置对话框的内容面板
        dialog.setContentPane(jPanel);
        // 显示对话框
        dialog.setVisible(true);
    }


    private void createSetScanPanel(JDialog dialog, JButton saveButton){
        scanSetPanel = new JPanel(new BorderLayout());
        scanSetPanel.setBorder(BorderFactory.createTitledBorder(""));
        String scanInterval = meetingInfo.getAutoScanInterval();

        JPanel topPanel = new JPanel(new GridLayout(2,1,20,10));
        JPanel firstRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(firstRowPanel);
        JLabel mcuNameLabel = new JLabel(Constants.MCU_NAME_LABEL + "：");
        firstRowPanel.add(mcuNameLabel);
        JLabel mcuName = new JLabel(mcuInfo.getMcuName());
        firstRowPanel.add(mcuName);
        firstRowPanel.add(new JLabel("              "));

        JLabel mcuIpLabel = new JLabel(Constants.MCU_ADDRESS_LABEL + "：");
        firstRowPanel.add(mcuIpLabel);
        JLabel mcuIpValue = new JLabel(mcuInfo.getMcuIp());
        firstRowPanel.add(mcuIpValue);
        firstRowPanel.add(new JLabel("              "));

        JLabel mcuPortLabel = new JLabel(Constants.MCU_PORT_LABEL + "：");
        firstRowPanel.add(mcuPortLabel);
        JLabel mcuPort = new JLabel(mcuInfo.getPort());
        firstRowPanel.add(mcuPort);
        firstRowPanel.add(new JLabel("              "));

        JLabel mcuOutPortLabel = new JLabel(Constants.MCU_PORT_OUT_LABEL + "：");
        firstRowPanel.add(mcuOutPortLabel);
        JLabel mcuOutPort = new JLabel(mcuInfo.getOutPort());
        firstRowPanel.add(mcuOutPort);

        JPanel secondRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel scanIntervalLabel = new JLabel(Constants.MCU_SCAN_INTERVAL_LABEL + "：");
        JComboBox scanIntervalBox = new JComboBox(Constants.MCU_SCAN_LIST);
        scanIntervalBox.setPreferredSize(new Dimension(80,20));
        scanIntervalBox.setSelectedItem(scanInterval);
        secondRowPanel.add(scanIntervalLabel);
        secondRowPanel.add(scanIntervalBox);
        topPanel.add(secondRowPanel);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JScrollPane leftJlistPanel = new JScrollPane();
        leftJlistPanel.setBorder(BorderFactory.createTitledBorder(Constants.ALL_TERMINAL_LIST));
        leftJlistPanel.setPreferredSize(new Dimension(250, 400));
        JList leftJlist = new JList();
        leftJlist.setPreferredSize(new Dimension(100, 200));
        ParticipantListModel leftParticipantListModel = new ParticipantListModel();
        leftParticipantListModel.setParticipantList(leftDataList);
        leftJlist.setModel(leftParticipantListModel);
        leftJlist.setCellRenderer(new CustomCellRender());
        leftJlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        leftJlistPanel.setViewportView(leftJlist);
        centerPanel.add(leftJlistPanel);

        JPanel centerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerButtonPanel.setPreferredSize(new Dimension(110, 300));
        centerPanel.add(centerButtonPanel);

        JLabel tmpLabel1 = new JLabel();
        tmpLabel1.setPreferredSize(new Dimension(100,30));
        centerButtonPanel.add(tmpLabel1);
        centerButtonPanel.add(tmpLabel1);

        JPanel allButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        allButtonPanel.setBorder(BorderFactory.createTitledBorder(Constants.BUTTON_ALL_ZONE));
        allButtonPanel.setPreferredSize(new Dimension(110, 160));
        centerButtonPanel.add(allButtonPanel);

        JButton rightButton = new JButton(Constants.BUTTON_RIGHT_MOVE);
        rightButton.setPreferredSize(new Dimension(100, 30));
        allButtonPanel.add(rightButton);

        JButton leftButton = new JButton(Constants.BUTTON_LEFT_MOVE);
        leftButton.setPreferredSize(new Dimension(100, 30));
        allButtonPanel.add(leftButton);

        JButton allRightButton = new JButton(Constants.BUTTON_ALL_RIGHT_MOVE);
        allRightButton.setPreferredSize(new Dimension(100, 30));
        allButtonPanel.add(allRightButton);

        JButton allLeftButton = new JButton(Constants.BUTTON_ALL_LEFT_MOVE);
        allLeftButton.setPreferredSize(new Dimension(100, 30));
        allButtonPanel.add(allLeftButton);

        centerButtonPanel.add(tmpLabel1);
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rightButtonPanel.setBorder(BorderFactory.createTitledBorder(Constants.BUTTON_RIGHT_ZONE));
        rightButtonPanel.setPreferredSize(new Dimension(110, 100));
        centerButtonPanel.add(rightButtonPanel);

        JButton upButton = new JButton(Constants.BUTTON_UP_MOVE);
        upButton.setPreferredSize(new Dimension(100, 30));
        rightButtonPanel.add(upButton);

        JButton downButton = new JButton(Constants.BUTTON_DOWN_MOVE);
        downButton.setPreferredSize(new Dimension(100, 30));
        rightButtonPanel.add(downButton);

        JScrollPane rightJlistPanel = new JScrollPane();
        rightJlistPanel.setBorder(BorderFactory.createTitledBorder(Constants.SCAN_TERMINAL_LIST));
        rightJlistPanel.setPreferredSize(new Dimension(250, 400));
        JList rightJlist = new JList();
        rightJlist.setPreferredSize(new Dimension(100, 200));
        ParticipantListModel rightParticipantListModel = new ParticipantListModel();
        rightParticipantListModel.setParticipantList(rightDataList);
        rightJlist.setModel(rightParticipantListModel);
        rightJlist.setCellRenderer(new CustomCellRender());
        rightJlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        rightJlistPanel.setViewportView(rightJlist);
        centerPanel.add(rightJlistPanel);

        scanSetPanel.add(topPanel, BorderLayout.NORTH);
        scanSetPanel.add(centerPanel, BorderLayout.CENTER);

        scanSetPanel.updateUI();

        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = rightJlist.getSelectedIndices();
                if(selectedIndices.length == 0 || selectedIndices[0] == 0){
                    return;
                }
                ParticipantListModel rightParticipantListModel = (ParticipantListModel) rightJlist.getModel();
                List<ParticipantInfo> participantList = rightParticipantListModel.getParticipantList();
                List<ParticipantInfo> newScanList = swap(participantList, selectedIndices, true);
                rightParticipantListModel.setParticipantList(newScanList);
                rightJlist.setModel(rightParticipantListModel);
                for(int i=0,len=selectedIndices.length; i<len; i++){
                    selectedIndices[i]--;
                }
                rightJlist.setSelectedIndices(selectedIndices);
                rightJlist.updateUI();
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = rightJlist.getSelectedIndices();
                ParticipantListModel rightParticipantListModel = (ParticipantListModel) rightJlist.getModel();
                List<ParticipantInfo> participantList = rightParticipantListModel.getParticipantList();
                if(selectedIndices.length == 0 || selectedIndices[selectedIndices.length - 1] == (participantList.size()-1)){
                    return;
                }
                List<ParticipantInfo> newScanList = swap(participantList, selectedIndices, false);
                rightParticipantListModel.setParticipantList(newScanList);
                rightJlist.setModel(rightParticipantListModel);
                for(int i=0,len=selectedIndices.length; i<len; i++){
                    selectedIndices[i]++;
                }
                rightJlist.setSelectedIndices(selectedIndices);
                rightJlist.updateUI();
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                    exchangeJlistData(leftJlist, rightJlist);
            }
        });
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exchangeJlistData(rightJlist, leftJlist);
            }
        });
        allRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allExchangeJlistData(leftJlist, rightJlist);
            }
        });
        allLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allExchangeJlistData(rightJlist, leftJlist);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //保存轮询列表
                ParticipantListModel rightParticipantListModel = (ParticipantListModel) rightJlist.getModel();
                List<ParticipantInfo> participantList = rightParticipantListModel.getParticipantList();
                List<String> scanList = new ArrayList<>();
                for(ParticipantInfo part : participantList){
                    scanList.add(part.getId());
                }
                String interval = (String)scanIntervalBox.getSelectedItem();
                AutoScanInfo autoScanInfo = new AutoScanInfo();
                autoScanInfo.setAutoScanInterval(interval);
                autoScanInfo.setScanList(scanList);
                String setScanUrl = "http://" + mcuIp +"/portal/rest/meetings/"+meetingInfo.getId()+"/autoScan";
                try {
                    HttpClientHelper.doPutWithRole(setScanUrl, autoScanInfo);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, mcuIp + " 设置轮巡列表出现错误！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                }
                dialog.dispose();
            }
        });
    }

    private List<ParticipantInfo> swap(List<ParticipantInfo> scanList, int[] selectedIndices, boolean flag){
        int targetIndex = -1;
        List<Integer> selectedList = new ArrayList();
        selectedList.addAll(selectedList);
        List<ParticipantInfo> selectedPartList = new ArrayList<>();
        List<ParticipantInfo> newPartList = new ArrayList<>();
        for(int index : selectedIndices){
            selectedList.add(index);
            selectedPartList.add(scanList.get(index));
        }
        if(flag){//向上
            targetIndex = selectedIndices[0] - 1;
            for(int i=0,len=scanList.size(); i<len; i++){
                if(i == targetIndex){
                    newPartList.addAll(selectedPartList);
                    newPartList.add(scanList.get(i));
                }else if(selectedList.contains(i)){
                    continue;
                }else{
                    newPartList.add(scanList.get(i));
                }
            }
        }else{//向下
            targetIndex = selectedIndices[selectedIndices.length-1] + 1;
            for(int i=0,len=scanList.size(); i<len; i++){
                if(i == targetIndex){
                    newPartList.add(scanList.get(i));
                    newPartList.addAll(selectedPartList);
                }else if(selectedList.contains(i)){
                    continue;
                }else{
                    newPartList.add(scanList.get(i));
                }
            }
        }
        return newPartList;
    }

    private void exchangeJlistData(JList fromJList, JList toJList){
        // 获取所有被选中的选项索引
        int[] indices = fromJList.getSelectedIndices();
        if(indices.length == 0){
            return;
        }
        // 获取选项数据的 ListModel
        ParticipantListModel fromParticipantListModel = (ParticipantListModel)fromJList.getModel();
        ParticipantListModel toParticipantListModel = (ParticipantListModel)toJList.getModel();
        List<ParticipantInfo> tmpParticipantList = new ArrayList<>();
        for(int index : indices){
            toParticipantListModel.addElementAt(fromParticipantListModel.getElementAt(index));
            tmpParticipantList.add(fromParticipantListModel.getElementAt(index));
        }
        for(ParticipantInfo part : tmpParticipantList){
            fromParticipantListModel.removeElement(part);
        }
        fromJList.setModel(fromParticipantListModel);
        toJList.setModel(toParticipantListModel);
        fromJList.updateUI();
        toJList.updateUI();
    }

    private void allExchangeJlistData(JList fromJList, JList toJList){
        ParticipantListModel fromParticipantListModel = (ParticipantListModel)fromJList.getModel();
        ParticipantListModel toParticipantListModel = (ParticipantListModel)toJList.getModel();
        for(ParticipantInfo participantInfo : fromParticipantListModel.getParticipantList()){
            toParticipantListModel.addElementAt(participantInfo);
        }
        fromParticipantListModel.getParticipantList().clear();
        fromJList.setModel(fromParticipantListModel);
        toJList.setModel(toParticipantListModel);
        fromJList.updateUI();
        toJList.updateUI();
    }
}
