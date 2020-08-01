package th0730;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpServletResponse {
	private OutputStream out;
	private int status;
	private String msg;
	private Map<String, String> headerMap = new HashMap<>();
	private List<Cookie> cookieList = new ArrayList<>();

	private CharArrayWriter caw = new CharArrayWriter();
	private PrintWriter pw = new PrintWriter(caw);

	public HttpServletResponse(OutputStream out) {
		this.out = out;
	}

	// 获取响应输出流（打印
	public PrintWriter getWriter() {
		return pw;
	}

	// 设置结果码与结果码消息
	public void setStatus(int status, String msg) {
		if (this.status == 0) {
		this.status = status;
		this.msg = msg;
		}
	}

	// 将响应报文发送给浏览器
	public void flushBuffer() throws IOException {
		// 响应头行
		out.write(("HTTP/1.1 " + status + " " + msg + " OK\n").getBytes());
		// 响应头域
		out.write(("Content-Type: text/html; charset=utf-8\n").getBytes());
		// 将头域集合中的值写入响应报文
		for (Entry<String, String> entry : headerMap.entrySet()) {
			out.write((entry.getKey() + ": " + entry.getValue() + "\n").getBytes());

		}
		//迭代器循环
		for (Iterator iterator = cookieList.iterator(); iterator.hasNext();) {
			Cookie cookie = (Cookie) iterator.next();
			out.write(cookie.toString().getBytes());
		}
		
		// 空行CRLF
		out.write("\n".getBytes());
		// 实体
		out.write(caw.toString().getBytes());
	}

	// 响应重定向方法
	public void sendRedirect(String uri) {
		
			// 写结果码 301 or 302
			setStatus(301, "Redirect");
			// 在头域中写入location：要转的页面
			headerMap.put("Location", uri);
		
	}

	public void addCookie(Cookie cookie) {
		cookieList.add(cookie);
		
	}

}
