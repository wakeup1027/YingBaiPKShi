package lxq.admin.controller;

import com.base.BaseController;
import com.config.ControllerBind;
import com.jfinal.aop.Before;
import demo.AdminInterceptor;

@Before(AdminInterceptor.class)
@ControllerBind(controllerKey = "/AdminStrUrl")
public class EasyUI_Controller extends BaseController{
	
	public void index(){
		render("/admin/EasyUI/index.html");
	}
		
}