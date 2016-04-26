package cn.edu.gdut.filter;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.gdut.util.LocalCache;
import cn.edu.gdut.util.QueryUrlUtil;

public class HtmlStaticFilter extends FilterBase {

	private static LocalCache<String, String> lc = new LocalCache<String, String>("Html Static",60*1000L);
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public void doFilter(HttpServletRequest request,HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String url = QueryUrlUtil.buildQueryUrl(request);
		if (needStaticHtml(url)){
			String html = lc.get(url);
			if (html == null){
				ResponseWrapper wrapper = new ResponseWrapper(response);
				chain.doFilter(request, wrapper);
				html = wrapper.getResult();
				lc.put(url, html);
			} else {
				logger.info("Static Html Cache Success");
			}
			PrintWriter out = response.getWriter();
			out.print(html);
			out.flush();
			out.close();
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean needStaticHtml(String url) {
		return false;
	}

	class ResponseWrapper extends HttpServletResponseWrapper {  
	    private PrintWriter cachedWriter;  
	    private CharArrayWriter bufferedWriter;  
	  
	    public ResponseWrapper(HttpServletResponse response) {  
	        super(response);  
	        bufferedWriter = new CharArrayWriter();      
	        cachedWriter = new PrintWriter(bufferedWriter);  
	    }  
	  
	    @Override  
	    public PrintWriter getWriter() throws IOException {  
	        return cachedWriter;  
	    }  
	      
	    public String getResult() {  
	        return bufferedWriter.toString();  
	    }  
	}  
}
