package com.klst.client.util;

public class Constants {

    //控件名称常量
    public static final String CLIENT_TITLE = "自动轮巡网管";

    public static final String MCU_PANEL_NAME = "MCU配置区";

    public static final String MATRIX_PANEL_NAME = "矩阵配置区";

    public static final String SCAN_LIST_NAME = "MCU终端轮巡区";

    public static final String ACTION_PANEL = "开始/结束轮巡";

    public static final String START_SCAN = "开始轮巡";

    public static final String STOP_SCAN = "结束轮巡";

    public static final String SUSPEND_SCAN = "暂停轮巡";

    public static final String CONTINUE_SCAN = "继续轮巡";

    public static final String SCANING = "轮巡中...";

    public static final String NO_SCAN = "未轮巡";

    public static final String SCAN_STATUS = "轮巡状态";

    public static final String SAVE_MCU = "新建MCU";

    public static final String ADD_MATRIX = "新增矩阵";

    public static final String SAVE_MATRIX = "保存矩阵";

    public static final String MATRIX_WARNING_MESSAGE = "（请配置矩阵信息！）";

    public static final String DELETE_MCU_ICON = "删除MCU";

    public static final String DELETE_MATRIX_ICON = "删除矩阵";

    public static final String DELETE_MCU_WARNING = "是否删除MCU？";

    public static final String DELETE_MATRIX_WARNING = "是否删除矩阵配置？";

    public static final String STOP_SCAN_WARNING = "是否结束轮巡？";

    public static final String SYSTEM_EXIT = "是否退出系统？";

    public static final String WARNING = "警告";

    public static final String MCU_UPDATE = "MCU配置";

    public static final String TERMINAL_LIST = "终端列表";

    public static final String ALL_TERMINAL_LIST = "所有与会者";

    public static final String SCAN_TERMINAL_LIST = "轮巡与会者";

    public static final String SET_SCAN_LIST = "设置轮巡列表";

    public static final String BUTTON_CONFIRM = "确定";

    public static final String BUTTON_CANCEL = "取消";

    public static final String BUTTON_RIGHT_MOVE = "右移";

    public static final String BUTTON_ALL_RIGHT_MOVE = "全部右移";

    public static final String BUTTON_LEFT_MOVE = "左移";

    public static final String BUTTON_ALL_LEFT_MOVE = "全部左移";

    public static final String BUTTON_UP_MOVE = "上移";

    public static final String BUTTON_DOWN_MOVE = "下移";

    public static final String MCU_UP_MOVE = "向上移动MCU顺序";

    public static final String MCU_DOWN_MOVE = "向下移动MCU顺序";

    public static final String MCU_NAME_LABEL = "MCU名称";

    public static final String MCU_ADDRESS_LABEL = "MCU地址";

    public static final String MCU_PORT_LABEL = "矩阵输入端口";

    public static final String MCU_PORT_OUT_LABEL = "矩阵输出端口";

    public static final String MCU_CONNECT_STATUS_LABEL = "在线状态";

    public static final String MCU_COMMON_STATUS_LABEL = "离线状态";

    public static final String MCU_SCAN_STATUS_LABEL = "轮巡状态";

    public static final String MCU_SCAN_INTERVAL_LABEL = "轮巡间隔（s）";

    public static final String BUTTON_ALL_ZONE = "两侧功能区";

    public static final String BUTTON_RIGHT_ZONE = "右侧功能区";

    //mcu轮巡
    //轮巡停止状态标识
    public static final String MCU_SCAN_STOP = "0";
    //轮巡开启状态标识
    public static final String MCU_SCAN_START = "1";
    //轮巡暂停状态标识
    public static final String MCU_SCAN_SUSPEND = "2";

    public static final String[] MCU_SCAN_LIST = new String[]{"5", "10", "20", "30", "60", "90"};

    public static final String MCU_IP_VERIFY = "(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))";

    public static final String NUMBER_VERIFY = "^[0-9]*[1-9][0-9]*$";

    //event事件类型
    public static final String EVENT_MEETINGCHANGED = "MeetingChanged";

    public static final String EVENT_POLLINGPART = "PollingPart";

    public static final String EVENT_PARTCOUNTCHANGED = "PartCountChanged";

    public static final String EVENT_MEETING_UPDATED = "Updated";

    public static final String EVENT_MEETING_DELETED= "Deleted";

    public static final String EVENT_MEETINGPARTICIPANTCHANGED = "MeetingParticipantChanged";

    public static final String EVENT_PARTSTATUSCHANGED = "PartStatusChanged";

    public static final String EVENT_PART_DELETED= "Deleted";

    public static final String EVENT_PART_CREATED= "Created";

    //文件常量
    //mcu列表文件
    public static final String MCU_JSON_FILE = "mcus.json";
    //矩阵文件
    public static final String MATRIX_JSON_FILE = "matrix.json";
}
