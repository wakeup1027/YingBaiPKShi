package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "userinfo")
public class UserInfo extends Model<UserInfo>{
	private static final long serialVersionUID = 1L;
	public static final UserInfo dao = new UserInfo();
}
