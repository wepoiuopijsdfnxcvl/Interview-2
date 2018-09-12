package com.klst.client.customplugin;

import com.klst.client.entity.McuInfo;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class McuInfoListModel  extends AbstractListModel<McuInfo> {

    private List<McuInfo> mcuInfoList = new ArrayList<>();

    @Override
    public int getSize() {
        return mcuInfoList.size();
    }

    @Override
    public McuInfo getElementAt(int index) {
        return mcuInfoList.get(index);
    }

    public void addElementAt(McuInfo cuInfo) {
        mcuInfoList.add(cuInfo);
    }

    public List<McuInfo> getMcuInfoList() {
        return mcuInfoList;
    }

    public void setMcuInfoList(List<McuInfo> mcuInfoList) {
        this.mcuInfoList = mcuInfoList;
    }
}
