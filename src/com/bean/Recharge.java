package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

/**
 * ��ֵ��¼
 * @author LXQ
 *
 */
@ModelBind(table = "recharge")
public class Recharge extends BaseModel<Recharge>{
	private static final long serialVersionUID = 1L;
	public static final Recharge dao = new Recharge();
}
