package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * ��ע��¼
 * @author LXQ
 *
 */
@ModelBind(table = "betsdatalog")
public class BetsDataLog extends Model<BetsDataLog>{
	private static final long serialVersionUID = 1L;
	public static final BetsDataLog dao = new BetsDataLog();
}
