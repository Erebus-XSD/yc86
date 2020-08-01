package th0728;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpServletResponse {
	private OutputStream out;
	private int status;
	private String msg;
	private CharArrayWriter caw = new CharArrayWriter();
	private PrintWriter pw = new PrintWriter(caw);

	public HttpServletResponse(OutputStream out) {
		this.out=out;
	}
	// 获取响应输出流（打印
	public PrintWriter getWriter() {
		return pw;
	}

	// 设置结果码与结果码消息
	public void setStatus(int statuc, String msg) {
		this.status = status;
		this.msg = msg;
	}

	// 将响应报文发送给浏览器
	public void flushBuffer() throws IOException {
		// 响应头行
		out.write(("HTTP/1.1 " + status + " " + msg + " OK\n").getBytes());
		// 响应头域
		out.write(("Content-Type: text/html; charset=utf-8\n").getBytes());
		// 空行CRLF
		out.write("\n".getBytes());
		// 实体
		out.write(caw.toString().getBytes());
	}

}
