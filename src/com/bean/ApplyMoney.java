package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "applymoney")
public class ApplyMoney extends Model<ApplyMoney>{
	private static final long serialVersionUID = 1L;
	public static final ApplyMoney dao = new ApplyMoney();
}
