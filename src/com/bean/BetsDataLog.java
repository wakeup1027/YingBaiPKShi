package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

/**
 * ÏÂ×¢¼ÇÂ¼
 * @author LXQ
 *
 */
@ModelBind(table = "betsdatalog")
public class BetsDataLog extends BaseModel<BetsDataLog>{
	private static final long serialVersionUID = 1L;
	public static final BetsDataLog dao = new BetsDataLog();
}
