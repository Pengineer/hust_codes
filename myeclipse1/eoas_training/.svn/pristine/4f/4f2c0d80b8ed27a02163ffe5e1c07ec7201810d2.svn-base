package csdc.tool.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareFilter;

/**
 * 当使用FCK上传文件时，跳过struts的拦截器，否则会出现bug无法上传
 * 
 * @author 雷达
 * 
 */
public class MyStrutsFilter extends StrutsPrepareFilter {

//	private boolean logFile(String name, String content) {
//		String filePath = ApplicationContainer.sc.getRealPath("") + "/upload/" + name.replaceAll("\\W", "_") + ".txt";
//		try {
//			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
//			out.write(content);
//			out.flush();
//			out.close();
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		
		if (session.getAttribute("client_ip") == null) {
			String ip = request.getHeader("x-forwarded-for");
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			session.setAttribute("client_ip", ip);
			session.setAttribute("server_name_port", req.getServerName() + ":" + req.getServerPort());
			System.out.println("client_ip: " + session.getAttribute("client_ip"));
			System.out.println("server_name_port: " + session.getAttribute("server_name_port") + "\n");
		}

//		Enumeration rnames=request.getParameterNames();
//		String tmp = "";
//		for (Enumeration e = rnames ; e.hasMoreElements() ;) {
//			String thisName=e.nextElement().toString();
//			String thisValue=request.getParameter(thisName);
//			tmp += thisName+" : "+thisValue+"\n";
//		}
//
//		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
//		String line = null;
//		StringBuilder sb = new StringBuilder();
//		while((line = br.readLine())!=null){
//			sb.append(line);
//		}
//		tmp += "\n\n" + sb.toString();
//
//		if (tmp.length() > 3) {
//			logFile(System.currentTimeMillis()+"_"+request.getRequestedSessionId() + "_" + request.getRequestURI(), tmp);
//		}


		String url = ((HttpServletRequest)req).getRequestURI();
		if (url.indexOf("fckeditor") >= 0 && url.indexOf("filemanager") >= 0 || url.indexOf("ReportServer") >= 0) {
			chain.doFilter(req, res);
        } else {
        	super.doFilter(req, res, chain);
        }
    }
}