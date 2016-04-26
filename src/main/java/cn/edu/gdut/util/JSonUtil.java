package cn.edu.gdut.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

public class JSonUtil {
	public static void writeToResponse(JsonObject jsonObject,
			HttpServletResponse response) {
		response.setContentType("application/json");
		try {
			response.getOutputStream().print(jsonObject.toString());
			response.getOutputStream().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeToResponse(String str, HttpServletResponse response) {
		response.setContentType("application/json");
		try {
			response.getOutputStream().print(str);
			response.getOutputStream().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
