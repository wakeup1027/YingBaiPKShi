package com.bean;

import com.config.ModelBind;
import com.jfinal.plugin.activerecord.Model;

@ModelBind(table = "tasktimer")
public class TaskTimerBean extends Model<TaskTimerBean>{
	
	private static final long serialVersionUID = 1L;
	public static final TaskTimerBean dao = new TaskTimerBean();

}
