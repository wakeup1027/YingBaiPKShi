package demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class AdminInterceptor implements Interceptor{

	public void intercept(Invocation ai) {
		Controller controller = ai.getController();

		HttpServletRequest request = controller.getRequest();
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("loginAdmin");
		if (obj == null) {
			controller.redirect("/alogin/login.html");
			return;
		}
		ai.invoke();
	}

}
