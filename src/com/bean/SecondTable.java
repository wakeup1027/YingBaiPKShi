package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "secondTable")
public class SecondTable  extends Model<SecondTable>{
	private static final long serialVersionUID = 1L;
	public static final SecondTable dao = new SecondTable();
}
