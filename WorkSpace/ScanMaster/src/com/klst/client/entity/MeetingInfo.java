package com.klst.client.entity;

import java.util.List;

public class MeetingInfo implements Comparable<MeetingInfo>{

	private String id;

	private String name;

	private String templateId;

	private boolean startNow;
	
	private long startTime;
	private long endTime;
	private TimeInfo startTimeStr;
	private TimeInfo endTimeStr;
	
	private int duration;

	private String meetingNumber;

	private String meetingPassword;

	private boolean visible;

	private String controlPassword;

	private String lecturerId;

	private String contentSenderId="";

	private boolean onlyParticipantsJoin;

	private String description;

	private MeetingStatus status;

	private boolean iframeSuppression;

	private int iframeSuppressionInterval;

	private Layout layout;
	private Caption caption;
	private SiteName siteName;
	private Record record;
	private RecordStatus recordStatus;
	
	private boolean locked;
	private boolean dialOutManually;

	// Redial
	private boolean autoRedial;
	private int autoRedialTimes;
	private int autoRedialInterval;
	
	// Video Setting
	private VideoMode videoMode;
	private String autoScanInterval;
	private String layoutRoleGuidCount;
	private List<LayoutGuid> layoutGuidList;

	// Customized polling, only for COP from Ver2.2
	private String scanCount;
	private List<String> scanList;
	private String owner;
	private ParticipanCount partCount;
	private List<VideoStream> streams;
	private boolean switching;
	private String videoSourceId;
	private boolean toCascadePort;
	private boolean slaveMeeting = false;
	private String pollingPartId;
	
	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public Caption getCaption() {
		return caption;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}

	public SiteName getSiteName() {
		return siteName;
	}

	public void setSiteName(SiteName siteName) {
		this.siteName = siteName;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isIframeSuppression() {
		return iframeSuppression;
	}

	public void setIframeSuppression(boolean iframeSuppression) {
		this.iframeSuppression = iframeSuppression;
	}

	public int getIframeSuppressionInterval() {
		return iframeSuppressionInterval;
	}

	public void setIframeSuppressionInterval(int iframeSuppressionInterval) {
		this.iframeSuppressionInterval = iframeSuppressionInterval;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public boolean isStartNow() {
		return startNow;
	}

	public void setStartNow(boolean startNow) {
		this.startNow = startNow;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getMeetingNumber() {
		return meetingNumber;
	}

	public void setMeetingNumber(String meetingNumber) {
		this.meetingNumber = meetingNumber;
	}

	public String getMeetingPassword() {
		return meetingPassword;
	}

	public void setMeetingPassword(String meetingPassword) {
		this.meetingPassword = meetingPassword;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getControlPassword() {
		return controlPassword;
	}

	public void setControlPassword(String controlPassword) {
		this.controlPassword = controlPassword;
	}

	public String getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(String lectureId) {
		this.lecturerId = lectureId;
	}

	public String getContentSenderId() {
		return contentSenderId;
	}

	public void setContentSenderId(String contentSenderId) {
		this.contentSenderId = contentSenderId;
	}

	public boolean isOnlyParticipantsJoin() {
		return onlyParticipantsJoin;
	}

	public void setOnlyParticipantsJoin(boolean onlyParticipantsJoin) {
		this.onlyParticipantsJoin = onlyParticipantsJoin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MeetingStatus getStatus() {
		return status;
	}

	public void setStatus(MeetingStatus status) {
		this.status = status;
	}

	public boolean isDialOutManually() {
		return dialOutManually;
	}

	public void setDialOutManually(boolean dialOutManually) {
		this.dialOutManually = dialOutManually;
	}

	public boolean isAutoRedial() {
		return autoRedial;
	}

	public void setAutoRedial(boolean autoRedial) {
		this.autoRedial = autoRedial;
	}

	public int getAutoRedialTimes() {
		return autoRedialTimes;
	}

	public void setAutoRedialTimes(int autoRedialTimes) {
		this.autoRedialTimes = autoRedialTimes;
	}

	public int getAutoRedialInterval() {
		return autoRedialInterval;
	}

	public void setAutoRedialInterval(int autoRedialInterval) {
		this.autoRedialInterval = autoRedialInterval;
	}

	public VideoMode getVideoMode() {
		return videoMode;
	}

	public void setVideoMode(VideoMode videoMode) {
		this.videoMode = videoMode;
	}

	public String getAutoScanInterval() {
		return autoScanInterval;
	}

	public void setAutoScanInterval(String autoScanInterval) {
		this.autoScanInterval = autoScanInterval;
	}

	public String getLayoutRoleGuidCount() {
		return layoutRoleGuidCount;
	}

	public void setLayoutRoleGuidCount(String layoutRoleGuidCount) {
		this.layoutRoleGuidCount = layoutRoleGuidCount;
	}

	public List<LayoutGuid> getLayoutGuidList() {
		return layoutGuidList;
	}

	public void setLayoutGuidList(List<LayoutGuid> layoutGuidList) {
		this.layoutGuidList = layoutGuidList;
	}

	public String getScanCount() {
		return scanCount;
	}

	public void setScanCount(String scanCount) {
		this.scanCount = scanCount;
	}

	public List<String> getScanList() {
		return scanList;
	}

	public void setScanList(List<String> scanList) {
		this.scanList = scanList;
	}

	public TimeInfo getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(TimeInfo startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public TimeInfo getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(TimeInfo endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public ParticipanCount getPartCount() {
		return partCount;
	}

	public void setPartCount(ParticipanCount partCount) {
		this.partCount = partCount;
	}

	public List<VideoStream> getStreams() {
		return streams;
	}

	public void setStreams(List<VideoStream> streams) {
		this.streams = streams;
	}

	public boolean isSwitching() {
		return switching;
	}

	public void setSwitching(boolean switching) {
		this.switching = switching;
	}

	public String getVideoSourceId() {
		return videoSourceId;
	}

	public void setVideoSourceId(String videoSourceId) {
		this.videoSourceId = videoSourceId;
	}

	public boolean isToCascadePort() {
		return toCascadePort;
	}

	public void setToCascadePort(boolean toCascadePort) {
		this.toCascadePort = toCascadePort;
	}

	public boolean isSlaveMeeting() {
		return slaveMeeting;
	}

	public void setSlaveMeeting(boolean isSlaveMeeting) {
		this.slaveMeeting = isSlaveMeeting;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public String getPollingPartId() {
		return pollingPartId;
	}

	public void setPollingPartId(String pollingPartId) {
		this.pollingPartId = pollingPartId;
	}

	@Override
	public String toString() {
		return "MeetingInfo{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", templateId='" + templateId + '\'' +
				", startNow=" + startNow +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", startTimeStr=" + startTimeStr +
				", endTimeStr=" + endTimeStr +
				", duration=" + duration +
				", meetingNumber='" + meetingNumber + '\'' +
				", meetingPassword='" + meetingPassword + '\'' +
				", visible=" + visible +
				", controlPassword='" + controlPassword + '\'' +
				", lecturerId='" + lecturerId + '\'' +
				", contentSenderId='" + contentSenderId + '\'' +
				", onlyParticipantsJoin=" + onlyParticipantsJoin +
				", description='" + description + '\'' +
				", status=" + status +
				", iframeSuppression=" + iframeSuppression +
				", iframeSuppressionInterval=" + iframeSuppressionInterval +
				", layout=" + layout +
				", caption=" + caption +
				", siteName=" + siteName +
				", record=" + record +
				", locked=" + locked +
				", dialOutManually=" + dialOutManually +
				", autoRedial=" + autoRedial +
				", autoRedialTimes=" + autoRedialTimes +
				", autoRedialInterval=" + autoRedialInterval +
				", videoMode=" + videoMode +
				", autoScanInterval='" + autoScanInterval + '\'' +
				", layoutRoleGuidCount='" + layoutRoleGuidCount + '\'' +
				", layoutGuidList=" + layoutGuidList +
				", scanCount='" + scanCount + '\'' +
				", scanList=" + scanList +
				", owner='" + owner + '\'' +
				", partCount=" + partCount +
				", streams=" + streams +
				", switching=" + switching +
				", videoSourceId='" + videoSourceId + '\'' +
				", toCascadePort=" + toCascadePort +
				", slaveMeeting=" + slaveMeeting +
				", pollingPartId=" + pollingPartId +
				'}';
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Override
	public int compareTo(MeetingInfo o) {
		if(o == null)
			return 0;
		if(o.getStartTime() >= getStartTime())
			return 1;
		else
			return -1;
	}

}

