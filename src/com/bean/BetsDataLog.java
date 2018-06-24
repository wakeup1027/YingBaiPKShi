package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * ÏÂ×¢¼ÇÂ¼
 * @author LXQ
 *
 */
@ModelBind(table = "betsdatalog")
public class BetsDataLog extends Model<BetsDataLog>{
	private static final long serialVersionUID = 1L;
	public static final BetsDataLog dao = new BetsDataLog();
}
