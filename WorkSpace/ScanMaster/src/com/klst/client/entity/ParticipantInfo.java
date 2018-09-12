package com.klst.client.entity;

/**
 * Created on 2015/7/8.
 */
public class ParticipantInfo {

    private String id;
    private String name;
    private ConnStatus status;
    private ProtocolType protocol;
    private String address;
    private H323Type h323Type;
    private String h323Alias="";
    private String e164="";
    private String sipUri="";
    private String videoSource = "";
    private String videoSourceName = "";
    private Cascade cascade;
    private DialDirection dialDirection;
    
    private boolean audioBlocked;
    private boolean audioMuted;
    private boolean speaker;
    private boolean videoBlocked;
    private boolean videoSecondary;
    
    private boolean encryption;
    private boolean fecc;
    private long connectionTime;
    private long disconnectionTime;
    private DisconnectCause disconnectCause;
    private String transmit;
    private String volume;
    private String videoCodec;
    private String audioCodec;
    private String dataCodec;
    private boolean forbiddenToBeLecture;
    private boolean contentToken;
    private String rate;
    private boolean saveToAddressbook;
    private boolean audioOnly;
    private boolean contentSending;
    
    private String meetingId;
    private boolean lecturer;
    private boolean moderator;

    private MeetingPtsType ptsType;

    private String masterPtsId;
    
    private String watchMode;
    
    private boolean isUltra;

    public MeetingPtsType getPtsType() {
        return ptsType;
    }

    public void setPtsType(MeetingPtsType ptsType) {
        this.ptsType = ptsType;
    }

    public boolean isForbiddenToBeLecture() {
        return forbiddenToBeLecture;
    }

    public void setForbiddenToBeLecture(boolean forbiddenToBeLecture) {
        this.forbiddenToBeLecture = forbiddenToBeLecture;
    }

    public boolean isContentToken() {
        return contentToken;
    }

    public void setContentToken(boolean contentToken) {
        this.contentToken = contentToken;
    }

    public String getId() {
        return id;
    }

    public Cascade getCascade() {
        return cascade;
    }

    public void setCascade(Cascade cascade) {
        this.cascade = cascade;
    }

    public String getSipUri() {
        return sipUri;
    }

    public String getH323Alias() {
        return h323Alias;
    }

    public void setH323Alias(String h323Alias) {
        this.h323Alias = h323Alias;
    }

    public String getE164() {
        return e164;
    }

    public void setE164(String e164) {
        this.e164 = e164;
    }

    public void setSipUri(String sipUri) {
        this.sipUri = sipUri;
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

    public ConnStatus getStatus() {
        return status;
    }

    public void setStatus(ConnStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }

    public DialDirection getDialDirection() {
        return dialDirection;
    }

    public void setDialDirection(DialDirection dialDirection) {
        this.dialDirection = dialDirection;
    }

    public boolean isEncryption() {
        return encryption;
    }

    public void setEncryption(boolean encryption) {
        this.encryption = encryption;
    }

    public boolean isFecc() {
        return fecc;
    }

    public void setFecc(boolean fecc) {
        this.fecc = fecc;
    }

    public long getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(long connectionTime) {
        this.connectionTime = connectionTime;
    }

    public long getDisconnectionTime() {
        return disconnectionTime;
    }

    public void setDisconnectionTime(long disconnectionTime) {
        this.disconnectionTime = disconnectionTime;
    }

    public String getTransmit() {
        return transmit;
    }

    public void setTransmit(String transmit) {
        this.transmit = transmit;
    }

    public ProtocolType getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolType protocol) {
        this.protocol = protocol;
    }

    public H323Type getH323Type() {
        return h323Type;
    }

    public void setH323Type(H323Type h323Type) {
        this.h323Type = h323Type;
    }

	public DisconnectCause getDisconnectCause() {
		return disconnectCause;
	}

	public void setDisconnectCause(DisconnectCause disconnectCause) {
		this.disconnectCause = disconnectCause;
	}
	
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getVideoCodec() {
		return videoCodec;
	}

	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}

	public String getAudioCodec() {
		return audioCodec;
	}

	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}

	public String getDataCodec() {
		return dataCodec;
	}

	public void setDataCodec(String dataCodec) {
		this.dataCodec = dataCodec;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public boolean isSaveToAddressbook() {
		return saveToAddressbook;
	}

	public void setSaveToAddressbook(boolean saveToAddressbook) {
		this.saveToAddressbook = saveToAddressbook;
	}

	public boolean isAudioBlocked() {
		return audioBlocked;
	}

	public void setAudioBlocked(boolean audioBlocked) {
		this.audioBlocked = audioBlocked;
	}

	public boolean isAudioMuted() {
		return audioMuted;
	}

	public void setAudioMuted(boolean audioMuted) {
		this.audioMuted = audioMuted;
	}

	public boolean isSpeaker() {
		return speaker;
	}

	public void setSpeaker(boolean speaker) {
		this.speaker = speaker;
	}

	public boolean isVideoBlocked() {
		return videoBlocked;
	}

	public void setVideoBlocked(boolean videoBlocked) {
		this.videoBlocked = videoBlocked;
	}

	public boolean isVideoSecondary() {
		return videoSecondary;
	}

	public void setVideoSecondary(boolean videoSecondary) {
		this.videoSecondary = videoSecondary;
	}

	public boolean isAudioOnly() {
		return audioOnly;
	}

	public void setAudioOnly(boolean audioOnly) {
		this.audioOnly = audioOnly;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}


	public boolean isContentSending() {
		return contentSending;
	}

	public void setContentSending(boolean contentSending) {
		this.contentSending = contentSending;
	}

	public boolean isLecturer() {
		return lecturer;
	}

	public void setLecturer(boolean lecturer) {
		this.lecturer = lecturer;
	}

	public boolean isModerator() {
		return moderator;
	}

	public void setModerator(boolean moderator) {
		this.moderator = moderator;
	}

	public String getVideoSourceName() {
		return videoSourceName;
	}

	public void setVideoSourceName(String videoSourceName) {
		this.videoSourceName = videoSourceName;
	}


    public String getMasterPtsId() {
        return masterPtsId;
    }

    public void setMasterPtsId(String masterPtsId) {
        this.masterPtsId = masterPtsId;
    }

    public String getWatchMode() {
		return watchMode;
	}

	public void setWatchMode(String watchMode) {
		this.watchMode = watchMode;
	}

	public boolean isUltra() {
		return isUltra;
	}

	public void setUltra(boolean isUltra) {
		this.isUltra = isUltra;
	}

	@Override
    public String toString() {
        return name;
    }
}
