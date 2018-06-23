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
		System.out.println("===����δ��¼�Ĺ���Ա===");
		if (obj == null) {
			System.out.println("�ù���Ա��û�е�½");
			controller.redirect("/alogin/login.html");
			return;
		}
		ai.invoke();
	}

}
