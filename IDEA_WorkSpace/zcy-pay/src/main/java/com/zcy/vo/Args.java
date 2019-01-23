package com.zcy.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Args implements Serializable {

	private static final long serialVersionUID = -6334502396034607544L;

	private List<Arg> arg;


}
