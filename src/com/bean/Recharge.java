package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * ��ֵ��¼
 * @author LXQ
 *
 */
@ModelBind(table = "recharge")
public class Recharge extends Model<Recharge>{
	private static final long serialVersionUID = 1L;
	public static final Recharge dao = new Recharge();
}
