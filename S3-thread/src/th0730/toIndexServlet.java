package th0730;

import java.io.PrintWriter;

public class toIndexServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		//实现响应重定向
		response.sendRedirect("/photo/index.html");
	}
}
