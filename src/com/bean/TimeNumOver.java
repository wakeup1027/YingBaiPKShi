package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "timenumover")
public class TimeNumOver extends Model<TimeNumOver>{
	private static final long serialVersionUID = 1L;
	public static final TimeNumOver dao = new TimeNumOver();
	
}
