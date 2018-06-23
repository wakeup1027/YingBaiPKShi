package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "lottery")
public class Lottery extends Model<Lottery>{

	private static final long serialVersionUID = 1L;
	public static final Lottery dao = new Lottery();
}
