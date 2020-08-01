package th0730;

import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String name=request.getParameter("name");
		PrintWriter out = response.getWriter();
		//在页面输出
		out.print("<h1>hello"+name+"<h1>");
		System.out.println("Hello World");
	}
}
