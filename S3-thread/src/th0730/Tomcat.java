package th0730;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Tomcat {
	
	private  HashMap<String,Servlet> servletMap;
	
	public  void startup() throws IOException {
		servletMap=new HashMap<>();
		servletMap.put("/photo/hello",new HelloServlet());
		servletMap.put("/",new toIndexServlet());
		
		servletMap.put("/cookie",new CookieServlet());
		servletMap.put("/login.jsp",new LoginPageServlet());
		
		servletMap.put("/photo/post.do",new LoginPageServlet());
		ServerSocket tomcat = new ServerSocket(8080);
		System.out.println("tomcat服务器启动完成，监听端口8080");
		boolean running = true;
		while (running) {

			Socket socket = tomcat.accept();
			new Thread() {
				public void run() {
					try {
						System.out.println("接受到请求");
						InputStream in = socket.getInputStream();
						OutputStream out = socket.getOutputStream();
						byte[] buffer = new byte[1024];
						int count;
						count = in.read(buffer);
						if (count > 0) {
							String requestText=new String(buffer,0,count);
							System.out.println(requestText);
							//解析请求报文
							HttpServletRequest request=buildRequest(requestText);
							HttpServletResponse response =new HttpServletResponse(out);
							String uri=request.getRequestURI();
							Servlet servlet = servletMap.get(uri);
							if(servlet !=null) {
								servlet.service(request,response);
							}else {
								processStaticRequest(request, out);
							}
							
						}
						socket.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				
			}.start();

		}
		tomcat.close();
	}
	public void shutdown() {
		
	}
	private HttpServletRequest buildRequest(String requestText) {
		// TODO Auto-generated method stub
		return new HttpServletRequest(requestText);
	}
	private void processStaticRequest(HttpServletRequest request, OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		String webpath=request.getRequestURI();
		String contentType;
		//结果码
		int statusCode=200;
		//定义磁盘文件路径
		String path = "E:/"+webpath;
		File file=new File(path);
		if(!file.exists()) {
			statusCode=404;
			path="E:/photo/404.html";
		}
		if(webpath.endsWith(".js")) {
			contentType="application/javascript; charset=utf-8";
		}else if(webpath.endsWith(".css")) {
			contentType="text/css; charset=utf-8";
		}else {
			contentType="text/html; charset=utf-8";
		}
		// 响应头行
		out.write(("HTTP/1.1 "+statusCode+" OK\n").getBytes());
		// 响应头域
		out.write(("Content-Type: "+contentType+"\n").getBytes());
		// 空行CRLF
		out.write("\n".getBytes());
		// 实体
		// out.write("<h1>Hello World</h1>".getBytes());
		
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[1024];
		int count;
		
		while ((count = fis.read(buffer)) > 0) {
			out.write(buffer, 0, count);
		}
		fis.close();

	}
	public static void main(String[] args) throws IOException {
		new Tomcat().startup();
	}
}
