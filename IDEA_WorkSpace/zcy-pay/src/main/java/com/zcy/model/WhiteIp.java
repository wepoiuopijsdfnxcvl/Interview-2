package com.zcy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class WhiteIp {
    private Integer id;

    private String apiKey;

    private String ipAddress;

    private Date createTime;

    private Integer status;

}