package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

@ModelBind(table = "answear")
public class Answear extends BaseModel<Answear>{
	private static final long serialVersionUID = 1L;
	public static final Answear dao = new Answear();
}