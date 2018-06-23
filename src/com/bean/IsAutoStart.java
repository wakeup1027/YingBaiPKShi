package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "isautostar")
public class IsAutoStart extends Model<IsAutoStart> {
	private static final long serialVersionUID = 1L;
	public static final IsAutoStart dao = new IsAutoStart();
}
