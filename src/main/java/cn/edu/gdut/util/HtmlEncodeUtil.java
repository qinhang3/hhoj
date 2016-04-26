package cn.edu.gdut.util;

public class HtmlEncodeUtil {
	public static String toHtml(String code){
		return code.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
