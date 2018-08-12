package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

@ModelBind(table = "opennumber")
public class OpenNumber extends BaseModel<OpenNumber>{
	private static final long serialVersionUID = 1L;
	public static final OpenNumber dao = new OpenNumber();
}
