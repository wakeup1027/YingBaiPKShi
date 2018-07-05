package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * 正在充值的表
 * @author LXQ
 *
 */
@ModelBind(table = "rechargenow")
public class RechargeNow  extends Model<RechargeNow>{
	private static final long serialVersionUID = 1L;
	public static final RechargeNow dao = new RechargeNow();
}
