package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

/**
 * 消息与联系客服
 * @author LXQ
 *
 */
@ModelBind(table = "message")
public class Message extends BaseModel<Message>{
	private static final long serialVersionUID = 1L;
	public static final Message dao = new Message();
}
