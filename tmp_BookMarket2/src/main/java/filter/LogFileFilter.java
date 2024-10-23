package filter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class LogFileFilter implements Filter {

	PrintWriter writer;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("LogFileFilter 초기화");
		String filename=filterConfig.getInitParameter("filename");
		
		if(filename==null) {
			throw new ServletException("로그 파일의 이름을 찾을 수 없습니다.");
		}
		try {
			FileWriter fw = new FileWriter(filename, true);
			writer = new PrintWriter(fw,true);
		} catch (IOException e) {
			throw new ServletException("로그 파일을 열 수 없습니다.");
		}
	}



	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		writer.println(" 접속한 클라이언트 IP : "+req.getRemoteAddr());
		long start = System.currentTimeMillis();
		writer.println(" 접근한 URL 경로 : "+getURLPath(req));
		writer.println(" 요청 처리 시작 : "+getCurrentTime());
		chain.doFilter(req, resp);
		
		long end = System.currentTimeMillis();
		writer.println(" 요청 처리 종류 시각 : "+getCurrentTime());
		writer.println(" 요청 처리 소요 시간 : "+(end-start)+"ms ");
		writer.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		
	}
	
	@Override
	public void destroy() {
		writer.close();
	}

	private String getURLPath(ServletRequest request) {
		HttpServletRequest req;
		String currentPath = "";
		String queryString = "";
		
		if(request instanceof HttpServletRequest) {
			req = (HttpServletRequest)request;
			currentPath = req.getRequestURI();
			queryString = req.getQueryString();
			
			 
			queryString = queryString==null? "" : ""+queryString;
//			if(queryString==null){
//				queryString="";
//			} else {
//				queryString =""+queryString;
//			}

		}
		
		return currentPath+queryString;
	}
	
	private String getCurrentTime() {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return formatter.format(calendar.getTime());
	}
}
