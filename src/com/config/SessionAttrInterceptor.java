package com.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 拦截用户是否用手机访问
 * 
 */
public class SessionAttrInterceptor implements Interceptor {

	public void intercept(Invocation ai) {

		Controller controller = ai.getController();

		HttpServletRequest request = controller.getRequest();
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("kuaisan_is_moile");
		if (obj == null) {
			boolean isMoile = HttpRequestDeviceUtils.isMobileDevice(request);
			session.setAttribute("kuaisan_is_moile", isMoile);
		}

		ai.invoke();
	}
	
}
