package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * ���ڳ�ֵ�ı�
 * @author LXQ
 *
 */
@ModelBind(table = "rechargenow")
public class RechargeNow  extends Model<RechargeNow>{
	private static final long serialVersionUID = 1L;
	public static final RechargeNow dao = new RechargeNow();
}
