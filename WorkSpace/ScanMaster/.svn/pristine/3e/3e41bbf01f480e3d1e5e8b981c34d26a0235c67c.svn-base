package com.klst.client.customplugin;

import com.klst.client.entity.McuInfo;
import com.klst.client.entity.ParticipantInfo;
import com.klst.client.service.McuScanService;
import com.klst.client.util.Constants;

import javax.swing.*;
import java.awt.*;

public class CustomCellRender  extends DefaultListCellRenderer{
    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if(value instanceof McuInfo){
            McuInfo mcuInfo = (McuInfo) value;
            setText((index+1) + "       " + mcuInfo.getMcuName());
            if(isSelected){
                if(McuScanService.mcuScanFlag != Constants.MCU_SCAN_STOP){
                    setBackground(new Color(248,251,108));
                    setForeground(new Color(247,111,96));
                }
            }
        }else if(value instanceof ParticipantInfo){
            ParticipantInfo participantInfo = (ParticipantInfo) value;
            setText(participantInfo.getName());
        }

        return this;
    }
}
