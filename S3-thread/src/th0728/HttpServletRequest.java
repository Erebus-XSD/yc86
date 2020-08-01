package th0728;

import java.util.HashMap;
import java.util.Map;

public class HttpServletRequest {
	private String method;
	private String requestUri;
	private String protocol;
	
	private Map<String,String> headerMap=new HashMap<>();
	
	public HttpServletRequest(String requestText) {
		//完成对请求报文的解析
		//通过换行来split报文
		String[] lines=requestText.split("\\n");
		//把头行用空格分开 
		String[] items=lines[0].split("\\s");
		//方法：get或者post
		method=items[0];
		//资源路径
		requestUri=items[1];
		//协议版本
		protocol=items[2];
		
		//对头域解析
		for(int i = 1 ; i<lines.length;i++) {
			
			lines[i]=lines[i].trim();
			//遇到空行结束头域
			if(lines[i].isEmpty()) {
				break;
			}
			//把内容切换为键值对保存
			items=lines[i].split(":");
			//用map集合保存头域值
			headerMap.put(items[0],items[1].trim());
		}
	}
	//获取请求方法名
	public String getMethod() {
		return method;
	}
	//获取请求资源路径
	public String getRequestURI() {
		return requestUri;
	}
	//获取协议版本
	public String getProtocol() {
		return protocol;
	}
	//获取头域值 键值对
	public String getHeader(String name) {
		return headerMap.get(name);
	}
	//获取请求参数
	public String getParameter(String name) {
		return null;
	}
	//获取请求cookie
	public Cookie[] getCookies() {
		return null;
	}
}
