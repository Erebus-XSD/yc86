package th0728;

import java.io.IOException;

public interface Servlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException ;
}
