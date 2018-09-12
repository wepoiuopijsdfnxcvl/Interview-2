package com.klst.client.component;

import com.klst.client.Thread.EventMessageReceiveTask;
import com.klst.client.customplugin.CustomCellRender;
import com.klst.client.customplugin.McuInfoListModel;
import com.klst.client.customplugin.McuListTableModel;
import com.klst.client.entity.McuInfo;
import com.klst.client.service.McuScanService;
import com.klst.client.util.Constants;
import com.klst.client.util.Log;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MainPanelCreate {

    private static final Logger LOGGER = Log.getLogger(MainPanelCreate.class.getSimpleName());

    private ImageIcon plusIcon=new ImageIcon(this.getClass().getResource("/img/plus.png"));

    private ImageIcon saveIcon=new ImageIcon(this.getClass().getResource("/img/save.png"));

    private ImageIcon deleteIcon=new ImageIcon(this.getClass().getResource("/img/delete.png"));

    private JFrame rootjFrame;

    private JPanel mainPanel;

    private JSplitPane jSplitPane;

    private JPanel scanPanel;

    private JButton addMcuButton;

    private JButton deleteMtrButton;

    private JList mcuJlist;

    private JButton startButton;

    private JButton stopButton;

    private JButton susButton;

    private JButton continueButton;

    private JLabel statusLabel;

    private JPanel flowPanel;

    private McuScanService mcuScanService = McuScanService.getInstance();

    private TerminalScanUpdate terminalScanUpdate;

    public MainPanelCreate(JFrame rootjFrame){
        this.rootjFrame = rootjFrame;

        //数据初始化
        mcuScanService.loadMcus();
        mcuScanService.loadMatrix();

        //mcu面板
        JPanel mcuPanel = getMcuPanel();

        //矩阵面板
        JPanel mtrPanel = getMtrPanel();

        JPanel mcuAndMtrPanel = new JPanel(new BorderLayout());
        mcuAndMtrPanel.add(mcuPanel, BorderLayout.CENTER);
        mcuAndMtrPanel.add(mtrPanel, BorderLayout.SOUTH);

        //MCU终端面板
        scanPanel = getScanPanel();

        //创建分割面板
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mcuAndMtrPanel, scanPanel);
        jSplitPane.setDividerSize(8);
        jSplitPane.setDividerLocation(300);
        jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setContinuousLayout(true);
        //分隔条是否可拖动
        jSplitPane.setEnabled(true);

        //创建开启、停止轮询面板
        JPanel buttonPanel = getButtonPanel();

        //创建主面板
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(jSplitPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        rootjFrame.setContentPane(mainPanel);

        //初始化MCU终端面板
        initMcuScanPanel();
    }


    private JPanel getMcuPanel(){
        JPanel mcuPanel = new JPanel(new BorderLayout());
        mcuPanel.setBorder(BorderFactory.createTitledBorder(Constants.MCU_PANEL_NAME));

        JPanel addMcuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addMcuButton = new JButton();
        addMcuButton.setIcon(plusIcon);
        addMcuButton.setToolTipText(Constants.MCU_UPDATE);
        addMcuPanel.add(addMcuButton);

        mcuPanel.add(addMcuPanel, BorderLayout.NORTH);

        JScrollPane mcuListPanel = new JScrollPane();
        mcuPanel.add(mcuListPanel, BorderLayout.CENTER);

        mcuJlist = new JList();
        mcuJlist.setPreferredSize(new Dimension(20,80));
        McuInfoListModel mcuInfoListModel = new McuInfoListModel();
        mcuInfoListModel.setMcuInfoList(mcuScanService.getMcuList());
        mcuJlist.setModel(mcuInfoListModel);
        mcuJlist.setCellRenderer(new CustomCellRender());
        mcuJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mcuListPanel.setViewportView(mcuJlist);
        mcuJlist.addListSelectionListener(new McuJlistActionListener());

        addMcuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateMcuDialog(rootjFrame);
            }
        });

        return mcuPanel;
    }

    private void initMcuScanPanel(){
        // 获取选项数据的 ListModel
        McuInfoListModel listModel = (McuInfoListModel)mcuJlist.getModel();
        if(listModel.getSize() > 0){
            mcuJlist.setSelectedIndex(0);
            LOGGER.info("Load Mcu Name : " + listModel.getElementAt(0).getMcuName() + ", IP = " + listModel.getElementAt(0).getMcuIp());
            terminalScanUpdate = new TerminalScanUpdate(rootjFrame, scanPanel, stopButton, listModel.getElementAt(0));
        }
    }

    private void showUpdateMcuDialog(Frame ownerPanel) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(ownerPanel, Constants.MCU_UPDATE, true);
        // 设置对话框的宽高
        double width = Toolkit.getDefaultToolkit().getScreenSize().width - 100;
        double height = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        dialog.setSize((int)width, (int)height);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(ownerPanel);

        JPanel jPanel = new JPanel(new BorderLayout());
        //创建MCU配置面板
        JButton saveButton = new JButton(Constants.BUTTON_CONFIRM);
        JPanel jpanel = getMcuUpdatePanel(dialog, saveButton);
        jPanel.add(jpanel, BorderLayout.CENTER);

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

    private JPanel getMcuUpdatePanel(JDialog dialog, JButton saveButton){
        JPanel jpanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setPreferredSize(new Dimension(800,50));
        JLabel mcuNameLabel = new JLabel(Constants.MCU_NAME_LABEL + "：");
        topPanel.add(mcuNameLabel);
        JTextField mcuNameText = new JTextField();
        mcuNameText.setPreferredSize(new Dimension(100,25));
        topPanel.add(mcuNameText);
        topPanel.add(new JLabel("   "));
        JLabel mcuAddressLabel = new JLabel(Constants.MCU_ADDRESS_LABEL + "：");
        topPanel.add(mcuAddressLabel);
        JTextField mcuAddressText = new JTextField();
        mcuAddressText.setPreferredSize(new Dimension(100,25));
        topPanel.add(mcuAddressText);
        topPanel.add(new JLabel("   "));
        JLabel mcuPortLabel = new JLabel(Constants.MCU_PORT_LABEL + "：");
        topPanel.add(mcuPortLabel);
        JTextField mcuPortText = new JTextField();
        mcuPortText.setPreferredSize(new Dimension(100,25));
        topPanel.add(mcuPortText);
        topPanel.add(new JLabel("   "));
        JLabel mcuOutPortLabel = new JLabel(Constants.MCU_PORT_OUT_LABEL + "：");
        topPanel.add(mcuOutPortLabel);
        JTextField mcuOutPortText = new JTextField();
        mcuOutPortText.setPreferredSize(new Dimension(100,25));
        topPanel.add(mcuOutPortText);
        topPanel.add(new JLabel("   "));
        JButton saveMcuButton = new JButton();
        saveMcuButton.setIcon(saveIcon);
        saveMcuButton.setToolTipText(Constants.SAVE_MCU);
        topPanel.add(saveMcuButton);
        topPanel.add(new JLabel("   "));
        JButton deleteMcuButton = new JButton();
        deleteMcuButton.setIcon(deleteIcon);
        deleteMcuButton.setToolTipText(Constants.DELETE_MCU_ICON);
        topPanel.add(deleteMcuButton);
        topPanel.add(new JLabel("   "));
        JButton upButton = new JButton("↑");
        upButton.setToolTipText(Constants.MCU_UP_MOVE);
        topPanel.add(upButton);
        topPanel.add(new JLabel("   "));
        JButton downButton = new JButton("↓");
        downButton.setToolTipText(Constants.MCU_DOWN_MOVE);
        topPanel.add(downButton);
        jpanel.add(topPanel,BorderLayout.NORTH);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JTable jTable =  McuListTableCreate.getInstance().createJTable(mcuScanService.getMcuList());
        ListSelectionModel listSelectionModel = jTable.getSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        jScrollPane.setViewportView(jTable);
        jpanel.add(jScrollPane,BorderLayout.CENTER);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                McuListTableModel mcuListTableModel = (McuListTableModel)jTable.getModel();
                Object[][] rowData = mcuListTableModel.getRowData();
                mcuScanService.setMcuList(McuListTableCreate.getMcuInfoList(rowData));
                mcuScanService.saveMcus();
                mcuScanService.loadMcus();
                McuInfoListModel mcuInfoListModel = (McuInfoListModel) mcuJlist.getModel();
                mcuInfoListModel.setMcuInfoList(mcuScanService.getMcuList());
                mcuJlist.setModel(mcuInfoListModel);
                mcuJlist.updateUI();

                dialog.dispose();
            }
        });

        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1){
                    int columnIndex = jTable.columnAtPoint(e.getPoint());
                    int rowIndex = jTable.rowAtPoint(e.getPoint());
                    if(columnIndex == 0){
                        McuListTableModel mcuListTableModel = (McuListTableModel)jTable.getModel();
                        Boolean isSelected =  (Boolean) mcuListTableModel.getValueAt(rowIndex,0);
                        Object[][] rowData = mcuListTableModel.getRowData();
                        rowData[rowIndex][0] = new Boolean(!isSelected);
                        mcuListTableModel.setRowData(rowData);
                        jTable.setModel(mcuListTableModel);
                        jTable.updateUI();
                    }
                }
            }
        });

        saveMcuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("".equals(mcuNameText.getText().trim())){
                    JOptionPane.showMessageDialog(null, "MCU名称不能为空！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if("".equals(mcuAddressText.getText().trim())){
                    JOptionPane.showMessageDialog(null, "MCU地址不能为空！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if("".equals(mcuPortText.getText().trim())){
                    JOptionPane.showMessageDialog(null, "矩阵输入端口不能为空！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if("".equals(mcuOutPortText.getText().trim())){
                    JOptionPane.showMessageDialog(null, "矩阵输出端口不能为空！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Pattern p = Pattern.compile(Constants.MCU_IP_VERIFY);
                if(!p.matcher(mcuAddressText.getText().trim()).matches()){
                    JOptionPane.showMessageDialog(null, "MCU地址格式不正确！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //矩阵输出端口校验
                p = Pattern.compile(Constants.NUMBER_VERIFY);
                String outPortStr = mcuOutPortText.getText().trim();
                String[] outPortArr = outPortStr.split(",", -1);
                HashMap<String, String> outPortMap = new HashMap<>();
                for(int i=0,len=outPortArr.length; i<len; i++){
                    if(!p.matcher(outPortArr[i]).matches()){
                        JOptionPane.showMessageDialog(null, "矩阵输出端口格式不正确！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(outPortMap.containsKey(outPortArr[i])){
                        JOptionPane.showMessageDialog(null, "矩阵输出端口重复！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    outPortMap.put(outPortArr[i], outPortArr[i]);
                }

                McuInfo mcuInfo = new McuInfo();
                mcuInfo.setMcuIp(mcuAddressText.getText().trim());
                mcuInfo.setMcuName(mcuNameText.getText().trim());
                mcuInfo.setPort(mcuPortText.getText().trim());
                mcuInfo.setOutPort(mcuOutPortText.getText().trim());
                McuListTableModel mcuListTableModel = (McuListTableModel)jTable.getModel();
                Object[][] rowData = mcuListTableModel.getRowData();
                List<McuInfo> mcuInfos = McuListTableCreate.getInstance().getMcuInfoList(rowData);
                for(McuInfo info : mcuInfos){
                    if(mcuInfo.getMcuName().equals(info.getMcuName())
                            || mcuInfo.getMcuIp().equals(info.getMcuIp())){
                        JOptionPane.showMessageDialog(null, mcuInfo.getMcuName() + " 名称或地址已存在！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                rowData = McuListTableCreate.getInstance().rowDataAdd(rowData, mcuInfo);
                mcuListTableModel.setRowData(rowData);
                jTable.setModel(mcuListTableModel);
                jTable.updateUI();
            }
        });

        deleteMcuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                McuListTableModel mcuListTableModel = (McuListTableModel)jTable.getModel();
                Object[][] rowData = mcuListTableModel.getRowData();
                List<Object[]> newRowDataList = new ArrayList<>();
                List<Object[]> selectedRowDataList = new ArrayList<>();
                for(int i=0,len=rowData.length; i<len; i++){
                    Boolean isSelected = (Boolean) rowData[i][0];
                    if(!isSelected){
                        newRowDataList.add(rowData[i]);
                    }else{
                        selectedRowDataList.add(rowData[i]);
                    }
                }
                if(selectedRowDataList.size() ==  0){
                    JOptionPane.showMessageDialog(null, " 请选择MCU记录！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int option = JOptionPane.showConfirmDialog(null, Constants.DELETE_MCU_WARNING,Constants.WARNING,2);
                if(option == 2){//0:确认 1：否 2：取消
                    return;
                }
                Object[][] newRowData = newRowDataList.toArray(new Object[0][]);
                mcuListTableModel.setRowData(newRowData);
                jTable.setModel(mcuListTableModel);
                jTable.updateUI();
            }
        });

        mcuNameText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    saveMcuButton.doClick();
                }
            }
        });

        mcuAddressText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    saveMcuButton.doClick();
                }
            }
        });

        mcuPortText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    saveMcuButton.doClick();
                }
            }
        });

        mcuOutPortText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    saveMcuButton.doClick();
                }
            }
        });

        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTable.getSelectedRow();
                if(selectedRow == -1 || selectedRow == 0){
                    return;
                }
                McuListTableModel mcuListTableModel = (McuListTableModel)jTable.getModel();
                Object[][] rowData = mcuListTableModel.getRowData();
                rowData = swapJtableData(rowData,selectedRow,true);
                mcuListTableModel.setRowData(rowData);
                jTable.setModel(mcuListTableModel);
                int toIndex = selectedRow - 1;
                jTable.getSelectionModel().setSelectionInterval(toIndex, toIndex);
                jTable.updateUI();
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTable.getSelectedRow();
                if(selectedRow == -1 || selectedRow == jTable.getRowCount()-1){
                    return;
                }
                McuListTableModel mcuListTableModel = (McuListTableModel)jTable.getModel();
                Object[][] rowData = mcuListTableModel.getRowData();
                rowData = swapJtableData(rowData,selectedRow,false);
                mcuListTableModel.setRowData(rowData);
                jTable.setModel(mcuListTableModel);
                int toIndex = selectedRow + 1;
                jTable.getSelectionModel().setSelectionInterval(toIndex, toIndex);
                jTable.updateUI();
            }
        });

        return jpanel;
    }

    private Object[][] swapJtableData(Object[][] rowData, int selectRowIndex, boolean flag){
        int toIndex = -1;
        Object[] tem = {};
        if(flag){
            toIndex = selectRowIndex - 1;
        }else{
            toIndex = selectRowIndex + 1;
        }
        tem = rowData[toIndex];
        rowData[toIndex] = rowData[selectRowIndex];
        rowData[selectRowIndex] = tem;
        return rowData;
    }

    private JPanel getMtrPanel(){
        String matrixInfo = mcuScanService.getMatrixInfo();
        JPanel mtrPanel = new JPanel();
        mtrPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton addMtrButton = new JButton();
        addMtrButton.setIcon(plusIcon);
        addMtrButton.setToolTipText(Constants.ADD_MATRIX);
        mtrPanel.add(addMtrButton);

        JLabel warningLabel = new JLabel(Constants.MATRIX_WARNING_MESSAGE);
        warningLabel.setForeground(Color.red);
        mtrPanel.add(warningLabel);

        JLabel matrLabel = new JLabel(matrixInfo);
        matrLabel.setPreferredSize(new Dimension(100, 10));
        matrLabel.setForeground(Color.blue);
        mtrPanel.add(matrLabel);
        deleteMtrButton = new JButton();
        deleteMtrButton.setIcon(deleteIcon);
        deleteMtrButton.setToolTipText(Constants.DELETE_MATRIX_ICON);
        mtrPanel.add(deleteMtrButton);

        JTextField mtrTextFiled = new JTextField(15);
        mtrPanel.add(mtrTextFiled);
        JButton saveMtrButton = new JButton();
        saveMtrButton.setIcon(saveIcon);
        saveMtrButton.setToolTipText(Constants.SAVE_MATRIX);
        mtrPanel.add(saveMtrButton);
        mtrTextFiled.setVisible(false);
        saveMtrButton.setVisible(false);
        if(matrixInfo != null && !"".equals(matrixInfo)){
            addMtrButton.setVisible(false);
            warningLabel.setVisible(false);
            matrLabel.setVisible(true);
            deleteMtrButton.setVisible(true);
        }else{
            addMtrButton.setVisible(true);
            warningLabel.setVisible(true);
            matrLabel.setVisible(false);
            deleteMtrButton.setVisible(false);
        }

        addMtrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMtrButton.setVisible(false);
                warningLabel.setVisible(false);
                mtrTextFiled.setText("");
                mtrTextFiled.setVisible(true);
                saveMtrButton.setVisible(true);
            }
        });

        saveMtrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mcuScanService.setMatrixInfo(mtrTextFiled.getText().trim());
                mcuScanService.saveMatrix();
                mcuScanService.loadMatrix();

                matrLabel.setText(mcuScanService.getMatrixInfo());
                mtrTextFiled.setVisible(false);
                saveMtrButton.setVisible(false);
                addMtrButton.setVisible(false);
                warningLabel.setVisible(false);
                matrLabel.setVisible(true);
                deleteMtrButton.setVisible(true);

                mtrPanel.updateUI();
            }
        });

        mtrTextFiled.addKeyListener(new KeyAdapter() {//回车监听
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    saveMtrButton.doClick();
                }
            }
        });

        deleteMtrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, Constants.DELETE_MATRIX_WARNING,Constants.WARNING,2);
                if(option == 2){//0:确认 1：否 2：取消
                    return;
                }
                mcuScanService.setMatrixInfo("");
                mcuScanService.saveMatrix();
                mcuScanService.loadMatrix();

                matrLabel.setText(mcuScanService.getMatrixInfo());
                mtrTextFiled.setVisible(false);
                saveMtrButton.setVisible(false);
                addMtrButton.setVisible(true);
                warningLabel.setVisible(true);
                matrLabel.setVisible(false);
                deleteMtrButton.setVisible(false);

                mtrPanel.updateUI();
            }
        });
        mtrPanel.setBorder(BorderFactory.createTitledBorder(Constants.MATRIX_PANEL_NAME));
        return mtrPanel;
    }

    private JPanel getScanPanel(){
        JPanel scanPanel = new JPanel(new BorderLayout());
        scanPanel.setBorder(BorderFactory.createTitledBorder(Constants.SCAN_LIST_NAME));
        return scanPanel;
    }

    private JPanel getButtonPanel(){
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder(Constants.ACTION_PANEL));
        flowPanel = new JPanel(new FlowLayout());

        startButton = new JButton(Constants.START_SCAN);
        startButton.addActionListener(new StartButtonActionListener());
        stopButton = new JButton(Constants.STOP_SCAN);
        stopButton.addActionListener(new StopButtonActionListener());
        susButton = new JButton(Constants.SUSPEND_SCAN);
        susButton.addActionListener(new SusButtonActionListener());
        continueButton = new JButton(Constants.CONTINUE_SCAN);
        continueButton.addActionListener(new ContinueButtonActionListener());
        JLabel statusTextLabel = new JLabel(Constants.SCAN_STATUS+" :");
        statusLabel = new JLabel();
        statusLabel.setText(Constants.NO_SCAN);
        statusLabel.setForeground(Color.GRAY);
        flowPanel.add(statusTextLabel);
        flowPanel.add(statusLabel);
        flowPanel.add(new JLabel("              "));
        flowPanel.add(startButton);
        flowPanel.add(stopButton);
        flowPanel.add(new JLabel("      "));
        flowPanel.add(susButton);
        flowPanel.add(continueButton);
        buttonPanel.add(flowPanel,BorderLayout.CENTER);

        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        susButton.setEnabled(false);
        continueButton.setEnabled(false);
        return buttonPanel;
    }

    private class McuJlistActionListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(e.getValueIsAdjusting()){//鼠标点击
                if(McuScanService.mcuScanFlag != Constants.MCU_SCAN_STOP){
                    return;
                }
                // 获取所有被选中的选项索引
                int[] indices = mcuJlist.getSelectedIndices();
                // 获取选项数据的 ListModel
                McuInfoListModel listModel = (McuInfoListModel)mcuJlist.getModel();
                // 输出选中的选项
                for (int index : indices) {
                    LOGGER.info("Load Mcu Name : " + listModel.getElementAt(index).getMcuName() + ", IP = " + listModel.getElementAt(index).getMcuIp());

                    TerminalScanUpdate.isStop = true;
                    EventMessageReceiveTask.isStop = true;
                    terminalScanUpdate = new TerminalScanUpdate(rootjFrame, scanPanel, stopButton, listModel.getElementAt(index));
                    return;
                }
            }
        }
    }

    private class StartButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_STOP){
                if(McuScanService.getInstance().getMcuList().size() == 0){
                    JOptionPane.showMessageDialog(null, "请配置MCU！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if("".equals(McuScanService.getInstance().getMatrixInfo())){
                    JOptionPane.showMessageDialog(null, "请配置矩阵信息！", Constants.WARNING,JOptionPane.WARNING_MESSAGE);
                    return;
                }
                LOGGER.info("start scan!");
                mcuScanService.startScan(rootjFrame, scanPanel, stopButton, mcuJlist);
                addMcuButton.setEnabled(false);
                deleteMtrButton.setEnabled(false);
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                susButton.setEnabled(true);
                continueButton.setEnabled(false);

                statusLabel.setText(Constants.SCANING);
                statusLabel.setForeground(new Color(247,111,96));
                flowPanel.updateUI();
            }
        }
    }

    private class StopButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_STOP){
                return;
            }
            int option = JOptionPane.showConfirmDialog(null, Constants.STOP_SCAN_WARNING,Constants.WARNING,2);
            if(option == 2){//0:确认 1：否 2：取消
                return;
            }
            LOGGER.info("stop scan!");
            mcuScanService.stopScan();
            addMcuButton.setEnabled(true);
            deleteMtrButton.setEnabled(true);
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            susButton.setEnabled(false);
            continueButton.setEnabled(false);

            statusLabel.setText(Constants.NO_SCAN);
            statusLabel.setForeground(Color.GRAY);
            flowPanel.updateUI();
            if(mcuScanService.getTerminalScanUpdate() != null && mcuScanService.getTerminalScanUpdate().getSetScanListButton() != null){
                mcuScanService.getTerminalScanUpdate().getSetScanListButton().setEnabled(true);
            }
        }
    }

    private class SusButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_START){
                LOGGER.info("suspend scan!");
                mcuScanService.suspendScan();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                susButton.setEnabled(false);
                continueButton.setEnabled(true);

                statusLabel.setText(Constants.SUSPEND_SCAN);
                statusLabel.setForeground(new Color(247,111,96));
                flowPanel.updateUI();
            }
        }
    }

    private class ContinueButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(McuScanService.mcuScanFlag == Constants.MCU_SCAN_SUSPEND){
                LOGGER.info("continue scan!");
                mcuScanService.continueScan();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                susButton.setEnabled(true);
                continueButton.setEnabled(false);

                statusLabel.setText(Constants.SCANING);
                statusLabel.setForeground(new Color(247,111,96));
                flowPanel.updateUI();
            }
        }
    }

}
