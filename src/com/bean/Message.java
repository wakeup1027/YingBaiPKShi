package com.bean;

import com.base.BaseModel;
import com.config.ModelBind;

/**
 * ��Ϣ����ϵ�ͷ�
 * @author LXQ
 *
 */
@ModelBind(table = "message")
public class Message extends BaseModel<Message>{
	private static final long serialVersionUID = 1L;
	public static final Message dao = new Message();
}
