package com.zcy.vo;

import lombok.*;

/**
 * @Author: Darrick
 * @Date: 2018/11/28 09:38
 * @Description:签名参数基类
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BaseSign {

    private String apiKey;
    private String sign;
    private String nonceStr;
    private Long timestamp;

}
