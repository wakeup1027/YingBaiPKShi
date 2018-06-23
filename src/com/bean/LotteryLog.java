package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "lottery_log")
public class LotteryLog extends Model<LotteryLog>{

	private static final long serialVersionUID = 1L;
	public static final LotteryLog dao = new LotteryLog();

}
