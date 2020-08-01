package th0730;

import java.io.IOException;

public interface Servlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException ;
}
