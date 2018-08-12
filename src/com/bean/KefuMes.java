package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

@ModelBind(table = "kefuMes")
public class KefuMes extends BaseModel<KefuMes>{
	private static final long serialVersionUID = 1L;
	public static final KefuMes dao = new KefuMes();
}
