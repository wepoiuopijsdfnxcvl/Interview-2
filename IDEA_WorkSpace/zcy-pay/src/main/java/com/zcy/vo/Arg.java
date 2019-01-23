package com.zcy.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@NoArgsConstructor
@Getter
@Setter
public class Arg implements Serializable {

	private static final long serialVersionUID = 8864973236712340606L;

	private String key;
	private String value;


}
