package com.klst.client.entity;

/**
 * Created by yzb on 2016/6/24.
 */
public class Record {

    private boolean enableRecord;
    private RecordMode recordMode = RecordMode.IMMEDIATELY;
    private boolean audioOnly;
    private String recordCallStream;
    private boolean enableRecordIndication;
    private boolean recordFailOrNoneEndConf;
    private String ip="";
    private RssAliasType rssAliasType = RssAliasType.E164;
    private String alias="";
    private String extension="";

    public boolean isEnableRecord() {
        return enableRecord;
    }

    public void setEnableRecord(boolean enableRecord) {
        this.enableRecord = enableRecord;
    }

    public RecordMode getRecordMode() {
        return recordMode;
    }

    public void setRecordMode(RecordMode recordMode) {
        this.recordMode = recordMode;
    }

    public boolean isAudioOnly() {
        return audioOnly;
    }

    public void setAudioOnly(boolean audioOnly) {
        this.audioOnly = audioOnly;
    }

    public String getRecordCallStream() {
        return recordCallStream;
    }

    public void setRecordCallStream(String recordCallStream) {
        this.recordCallStream = recordCallStream;
    }

    public boolean isEnableRecordIndication() {
        return enableRecordIndication;
    }

    public void setEnableRecordIndication(boolean enableRecordIndication) {
        this.enableRecordIndication = enableRecordIndication;
    }

    public boolean isRecordFailOrNoneEndConf() {
        return recordFailOrNoneEndConf;
    }

    public void setRecordFailOrNoneEndConf(boolean recordFailOrNoneEndConf) {
        this.recordFailOrNoneEndConf = recordFailOrNoneEndConf;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public RssAliasType getRssAliasType() {
        return rssAliasType;
    }

    public void setRssAliasType(RssAliasType rssAliasType) {
        this.rssAliasType = rssAliasType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
