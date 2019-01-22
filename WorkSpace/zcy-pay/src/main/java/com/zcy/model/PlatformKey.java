package com.zcy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PlatformKey {
    private Integer id;

    private String platformName;

    private String apiKey;

    private String secretKey;

    private Integer type;

    private Integer status;

    private String token;

    private Date createTime;

    private Date updateTime;

    private Date expireTime;

}