package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

@ModelBind(table = "applymoney")
public class ApplyMoney extends BaseModel<ApplyMoney>{
	private static final long serialVersionUID = 1L;
	public static final ApplyMoney dao = new ApplyMoney();
}
