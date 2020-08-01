package th0728;

import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = response.getWriter();
		//在页面输出
		out.print("<h1>hello<h1>");
		System.out.println("Hello World");
	}
}
