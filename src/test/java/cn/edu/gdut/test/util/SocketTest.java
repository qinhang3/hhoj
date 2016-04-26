package cn.edu.gdut.test.util;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.junit.Test;

import com.google.gson.Gson;

import cn.edu.gdut.model.StatusModel;

public class SocketTest {
	@Test
	public void test() throws Exception{
		Socket socket = new Socket("127.0.0.1", 2333);
		Scanner sin = new Scanner(socket.getInputStream());
		while(sin.hasNext()){
			String str = sin.nextLine();
			StatusModel statusModel = (StatusModel)new Gson().fromJson(str, StatusModel.class);
			System.out.println(statusModel.getCode());
		}
		
	}

}
