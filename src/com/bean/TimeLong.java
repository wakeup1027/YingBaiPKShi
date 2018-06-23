package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "timelong")
public class TimeLong extends Model<TimeLong>{

	private static final long serialVersionUID = 1L;
	public static final TimeLong dao = new TimeLong();
	
}
