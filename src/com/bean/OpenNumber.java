package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "opennumber")
public class OpenNumber extends Model<OpenNumber>{
	private static final long serialVersionUID = 1L;
	public static final OpenNumber dao = new OpenNumber();
}
