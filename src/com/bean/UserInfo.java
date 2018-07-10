package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

@ModelBind(table = "userinfo")
public class UserInfo extends BaseModel<UserInfo>{
	private static final long serialVersionUID = 1L;
	public static final UserInfo dao = new UserInfo();
}
