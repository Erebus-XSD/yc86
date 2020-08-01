package th0730;

import java.io.IOException;

public class LoginPageServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)  {
		Cookie[] cookies=request.getCookies();
		response.getWriter().append("用户名<input value='"+cookies[0].getValue()+"'><br>");
		response.getWriter().append("密码<input value=''><br>");
		response.getWriter().append("<input type='button' value='登录'><br>");
	}

}
