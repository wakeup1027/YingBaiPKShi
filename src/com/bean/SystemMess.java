package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

@ModelBind(table = "systemmess")
public class SystemMess extends BaseModel<SystemMess>{
	private static final long serialVersionUID = 1L;
	public static final SystemMess dao = new SystemMess();
}
