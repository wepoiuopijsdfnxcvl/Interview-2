package com.klst.client.entity;

public class PollingPartInfo {

	private String meetingId;

	private String scanMode;

	private String partId;

	private String scanFinish;

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getScanMode() {
		return scanMode;
	}

	public void setScanMode(String scanMode) {
		this.scanMode = scanMode;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getScanFinish() {
		return scanFinish;
	}

	public void setScanFinish(String scanFinish) {
		this.scanFinish = scanFinish;
	}

	@Override
	public String toString() {
		return "PollingPartInfo [meetingId=" + meetingId + ", scanMode=" + scanMode + ", partId=" + partId + ", scanFinish=" + scanFinish + "]";
	}

}
