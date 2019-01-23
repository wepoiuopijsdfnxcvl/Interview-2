package com.zcy.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Darrick
 * @Date: 2018/11/28 09:41
 * @Description:
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BaseRequset<T> {

    private T data;

    private BaseSign baseSign;

}
