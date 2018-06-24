package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "applymoneylog")
public class ApplyMoneyLog extends Model<ApplyMoneyLog>{
	private static final long serialVersionUID = 1L;
	public static final ApplyMoneyLog dao = new ApplyMoneyLog();
}
