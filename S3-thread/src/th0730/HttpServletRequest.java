package th0730;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServletRequest {
	private String method;
	private String requestUri;
	private String protocol;
	
	private Map<String,String> headerMap=new HashMap<>();
	private Map<String,String> paramsMap=new HashMap<>();
	
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
		
		
		int index=items[1].indexOf("?");
		if(index != -1) {
			//解析参数
			//把0到问号位置的参数传给requestUri
			requestUri=items[1].substring(0,index);
			//获取问号后面的参数
			String paramString=items[1].substring(index+1);
			//用&分割
			String[] params=paramString.split("&");
			for(int i=0;i<params.length;i++) {
				String[] nv = params[i].split("=");
				if(nv.length == 1) {
					paramsMap.put(nv[0],"");
				
				}else if(nv.length>1) {
					paramsMap.put(nv[0],nv[1]);
				}
			}
		}
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
		return paramsMap.get(name);
	}
	//获取请求cookie
	public Cookie[] getCookies() {
		String cookieString=headerMap.get("Cookie");
		if(cookieString ==null) {
			return null;
		}else {
			List<Cookie> cookieList=new ArrayList<>();
			String[] sCookies=cookieString.split(";\\s*");
			for (int i = 0; i < sCookies.length; i++) {
				String[] nv=sCookies[i].split("=");
				cookieList.add(new Cookie(nv[0],nv[1]));
			}
			return cookieList.toArray(new Cookie[0]);
		}
	}
}
