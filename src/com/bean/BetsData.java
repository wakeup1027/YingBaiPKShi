package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "betsdata")
public class BetsData extends Model<BetsData>{
	private static final long serialVersionUID = 1L;
	public static final BetsData dao = new BetsData();
}
