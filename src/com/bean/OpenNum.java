package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "opennum")
public class OpenNum extends Model<OpenNum> {
	private static final long serialVersionUID = 1L;
	public static final OpenNum dao = new OpenNum();
	
}
