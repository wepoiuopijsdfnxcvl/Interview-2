package com.klst.client.customplugin;

import com.klst.client.entity.ParticipantInfo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantListModel extends AbstractListModel<ParticipantInfo>{

    private List<ParticipantInfo> participantList = new ArrayList<>();

    @Override
    public int getSize() {
        return participantList.size();
    }

    @Override
    public ParticipantInfo getElementAt(int index) {
        return participantList.get(index);
    }

    public void addElementAt(ParticipantInfo participantInfo) {
        participantList.add(participantInfo);
    }

    public void insertElementAt(int index, ParticipantInfo participantInfo) {
        participantList.add(index, participantInfo);
    }

    public void removeElement(ParticipantInfo participantInfo) {
        participantList.remove(participantInfo);
    }


    public List<ParticipantInfo> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<ParticipantInfo> participantList) {
        this.participantList = participantList;
    }
}
