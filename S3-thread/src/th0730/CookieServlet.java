package th0730;

import java.io.IOException;

public class CookieServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		Cookie cookie=new Cookie("name","Ere");
		response.addCookie(cookie);
		response.getWriter().append("cookie");

	}

}
