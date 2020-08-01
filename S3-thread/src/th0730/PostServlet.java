package th0730;

import java.io.IOException;

public class PostServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		String name=request.getParameter("name");
		System.out.println("________");
		response.getWriter().append("post, name="+name);
		}

}
